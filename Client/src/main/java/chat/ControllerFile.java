/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author Administrator
 */



//create packages
//makesecret also byte
// changes to be made encrypt directly no split
//close bufferedredreader file

import java.io.RandomAccessFile;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import java.io.InputStream;
//import java.security.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.MessageDigest;

import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ControllerFile {
    int startFile(String fileName, String extension, int flag, String[] secretFile, int numberOfFiles) throws Exception{
        return new BEProject().mainStart(fileName,extension,flag, secretFile, numberOfFiles);
    }
    
    String[] startFile(String fileName, String extension, int flag) throws Exception{
        return new BEProject().mainStart(fileName,extension,flag);
    }
    
}

class DigitalSignature{
	KeyPair pair;
        PublicKey publicKey;
	DigitalSignature()throws Exception{
		this.pair = generateKeyPair();
	}
	DigitalSignature(byte[] publicKey, byte[] privateKey)throws Exception{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		PKCS8EncodedKeySpec PkeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory PkeyFactory = KeyFactory.getInstance("RSA");
		PrivateKey priKey = PkeyFactory.generatePrivate(PkeySpec);
		this.pair = new KeyPair(pubKey,priKey);
	}
        
        DigitalSignature(byte[] publicKey)throws Exception{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		this.publicKey = keyFactory.generatePublic(keySpec);
		//PKCS8EncodedKeySpec PkeySpec = new PKCS8EncodedKeySpec(privateKey);
		//KeyFactory PkeyFactory = KeyFactory.getInstance("RSA");
		//PrivateKey priKey = PkeyFactory.generatePrivate(PkeySpec);
		//this.pair = new KeyPair(pubKey);
	}
	
	PublicKey getPublicKey(){
		return this.pair.getPublic();
	}
	
	PrivateKey getPrivateKey(){
		return this.pair.getPrivate();
	}
	
    KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }
    byte[] sign(byte[] plainText) throws Exception {
		PrivateKey privateKey=this.pair.getPrivate();
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText);
        byte[] signature = privateSignature.sign();
		return signature;
    }
    boolean verify(byte[] plainText, byte[] signatureBytes) throws Exception {
		//PublicKey publicKey=this.pair.getPublic();
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(this.publicKey);
        publicSignature.update(plainText);
        return publicSignature.verify(signatureBytes);
    }
	byte[] getSHA(byte[] input) throws NoSuchAlgorithmException, Exception { 
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		return sign(md.digest(input));
	}
	boolean getSHA(byte[] input, byte[] digitalSignatureByte) throws NoSuchAlgorithmException, Exception { 
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return verify(md.digest(input),digitalSignatureByte);
	}
}
class EncryptionDecryptionAES{
	static Cipher cipher;
        
	public static byte[] getCipher(byte[] inputBytes, int flag, PrintWriter key_writer, FileOutputStream outDS, DigitalSignature ds) throws Exception {
		outDS.write(ds.getSHA(inputBytes));
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		String secret=Base64.getEncoder().encodeToString(secretKey.getEncoded());
		key_writer.println(secret);
		cipher = Cipher.getInstance("AES");
		byte [] encryptedByte = encrypt(inputBytes, secretKey);
		return encryptedByte;
	}
	
	public static byte[] getCipher(byte[] inputBytes, int flag, BufferedReader key_br, FileInputStream ds_br, DigitalSignature ds) throws Exception {
		String secret=key_br.readLine();
		byte[] secretKeyByte = Base64.getDecoder().decode(secret);
		SecretKey secretKey = new SecretKeySpec(secretKeyByte, 0, secretKeyByte.length, "AES");
		cipher = Cipher.getInstance("AES");
		byte [] decryptedByte = decrypt(inputBytes, secretKey);
		byte[] digitalSignatureByte = new byte[256];
		ds_br.read(digitalSignatureByte);
		boolean isSame=ds.getSHA(decryptedByte,digitalSignatureByte);
		if(isSame)
			System.out.println("Signature Authenticated");
                else{
                    System.out.println("Signature not Authenticated");
                    return new byte[0];
                }		
		return decryptedByte;
	}
        
        /* new keys as msg try*/
        public static byte[] getCipher(byte[] inputBytes, String[] secretKeyList, int fileNumber ,PrintWriter key_writer, FileOutputStream outDS, DigitalSignature ds) throws Exception {
		outDS.write(ds.getSHA(inputBytes));
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		String secret=Base64.getEncoder().encodeToString(secretKey.getEncoded());
		key_writer.println(secret);
                secretKeyList[fileNumber]=secret;
		cipher = Cipher.getInstance("AES");
		byte [] encryptedByte = encrypt(inputBytes, secretKey);
		return encryptedByte;
	}
	
	public static byte[] getCipher(byte[] inputBytes,String[] secretKeyList, int fileNumber, FileInputStream ds_br, DigitalSignature ds) throws Exception {
		String secret=secretKeyList[fileNumber];
                //System.out.println("Controller File:- "+secret);
		byte[] secretKeyByte = Base64.getDecoder().decode(secret);
		SecretKey secretKey = new SecretKeySpec(secretKeyByte, 0, secretKeyByte.length, "AES");
		cipher = Cipher.getInstance("AES");
		byte [] decryptedByte = decrypt(inputBytes, secretKey);
		byte[] digitalSignatureByte = new byte[256];
		ds_br.read(digitalSignatureByte);
		boolean isSame=ds.getSHA(decryptedByte,digitalSignatureByte);
		if(isSame)
			System.out.println("Signature Authenticated");
                else{
                    System.out.println("Signature not Authenticated");
                    return new byte[0];
                }		
		return decryptedByte;
	}

	public static byte[] encrypt(byte[] inputBytes, SecretKey secretKey) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(inputBytes);
		return encryptedByte;
	}

	public static byte[] decrypt(byte[] encryptedByte, SecretKey secretKey)throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedByte);
		return decryptedByte;
	}
}

class BEFile{
	String fileName;
	String fileExtension;
	String encryptionFolderName;
	BEFile(String encryptionFolderName, String fileName, String fileExtension){
		this.encryptionFolderName=encryptionFolderName;
		this.fileName=fileName;
		this.fileExtension=fileExtension;
	}
	public String [] splitFile() throws Exception{
		String fileName=this.fileName+this.fileExtension;
		RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        int numSplits = 3;
        long sourceSize = raf.length();
        long bytesPerSplit = sourceSize/numSplits;
        long remainingBytes = sourceSize % numSplits;
        int n=0;
        if(remainingBytes > 0){
            n=4;
        }else{
            n=3;
        }
        String file []=new String [n];
        int maxReadBufferSize = 8 * 500;
        for(int destIx=1; destIx <= numSplits; destIx++) {
			String nameSplitFile=encryptionFolderName+"//"+this.fileName+"_"+destIx+this.fileExtension;
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(nameSplitFile));
            file[destIx-1]=nameSplitFile;
            if(bytesPerSplit > maxReadBufferSize) {
                long numReads = bytesPerSplit/maxReadBufferSize;
                long numRemainingRead = bytesPerSplit % maxReadBufferSize;
                for(int i=0; i<numReads; i++) {
                    readWrite(raf, bw, maxReadBufferSize);
                }
                if(numRemainingRead > 0) {
                    readWrite(raf, bw, numRemainingRead);
                }
            }else {
                readWrite(raf, bw, bytesPerSplit);
            }
            bw.close();
        }
        if(remainingBytes > 0) {
			String nameSplitFile=encryptionFolderName+"//"+this.fileName+"_"+(numSplits+1)+this.fileExtension;
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(nameSplitFile));
            file[3]=nameSplitFile;
            readWrite(raf, bw, remainingBytes);
            bw.close();
        }
            raf.close();
        return file;
    }

    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if(val != -1) {
            bw.write(buf);
        }
    }
}
class BEProject{
    String[] mainStart(String fileName, String fileExtension, int flagEncryptDecrypt)throws Exception{
		String encryptionFolderName, decryptionFolderName, keysFolderName, digitalSinatureFolderName;

		encryptionFolderName="encryption";
		decryptionFolderName="decryption";
		keysFolderName="Keys";
		digitalSinatureFolderName="Digital_Signature";
		if(!new File(encryptionFolderName).isDirectory()){
			new File(encryptionFolderName).mkdirs();
		}
		if(!new File(decryptionFolderName).isDirectory()){
			new File(decryptionFolderName).mkdirs();
		}
		if(!new File(keysFolderName).isDirectory()){
			new File(keysFolderName).mkdirs();
		}
		if(!new File(digitalSinatureFolderName).isDirectory()){
			new File(digitalSinatureFolderName).mkdirs();
		}
		BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter File Name:-");
		//fileName=readConsole.readLine();
		System.out.println("Enter Extension:-");
		//fileExtension="."+readConsole.readLine();
		System.out.println("Choose 1.Encryption 2.Decryption");
		//flagEncryptDecrypt=Integer.parseInt(readConsole.readLine());//1 for encryption and 2 for decryption
		if(flagEncryptDecrypt==1){
			BEFile rawFile=new BEFile(encryptionFolderName,fileName, fileExtension);
			String split[]=rawFile.splitFile();
			DigitalSignature ds=new DigitalSignature();
			FileOutputStream outPrivate = new FileOutputStream(keysFolderName+"//"+fileName+".key");
			outPrivate.write(ds.getPrivateKey().getEncoded());
			outPrivate.close();
			FileOutputStream outPublic = new FileOutputStream(keysFolderName+"//"+fileName+ ".pub");
			outPublic.write(ds.getPublicKey().getEncoded());
			outPublic.close();
			PrintWriter keys_writer = new PrintWriter(keysFolderName+"//"+fileName+".txt", "UTF-8");
			keys_writer.println(split.length);
			FileOutputStream outDS = new FileOutputStream(digitalSinatureFolderName+"//"+fileName+".txt");
			EncryptionDecryptionAES enc=new EncryptionDecryptionAES();
                        
                        String [] secretKeyList=new String[split.length];
                        
			for(int i=0;i<split.length;i++){
				File inputFile = new File(split[i]);
				FileInputStream inputStream = new FileInputStream(inputFile);
				byte[] inputBytes = new byte[(int) inputFile.length()];
				inputStream.read(inputBytes);
				inputStream.close();			
				FileOutputStream outputStream = new FileOutputStream(inputFile);
				byte[] outputBytes=enc.getCipher(inputBytes,secretKeyList,i,keys_writer,outDS,ds);
				outputStream.write(outputBytes);
				outputStream.close();
			}
			keys_writer.close();
			outDS.close();
                        return secretKeyList;
		}
                return new String[0];
    }
    
    int mainStart(String fileName, String fileExtension, int flagEncryptDecrypt, String[] secretKeyList, int numberOfFiles )throws Exception{
		String encryptionFolderName, decryptionFolderName, keysFolderName, digitalSinatureFolderName;
				
		encryptionFolderName="encryption";
		decryptionFolderName="decryption";
		keysFolderName="Keys";
		digitalSinatureFolderName="Digital_Signature";
		if(!new File(encryptionFolderName).isDirectory()){
			new File(encryptionFolderName).mkdirs();
		}
		if(!new File(decryptionFolderName).isDirectory()){
			new File(decryptionFolderName).mkdirs();
		}
		if(!new File(keysFolderName).isDirectory()){
			new File(keysFolderName).mkdirs();
		}
		if(!new File(digitalSinatureFolderName).isDirectory()){
			new File(digitalSinatureFolderName).mkdirs();
		}
		//BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter File Name:-");
		//fileName=readConsole.readLine();
		System.out.println("Enter Extension:-");
		//fileExtension="."+readConsole.readLine();
		System.out.println("Choose 1.Encryption 2.Decryption");
		//flagEncryptDecrypt=Integer.parseInt(readConsole.readLine());//1 for encryption and 2 for decryption
		if(flagEncryptDecrypt==2){
			
			//BufferedReader keys_br = new BufferedReader(new FileReader(new File(keysFolderName+"//"+fileName+".txt")));
			FileInputStream ds_br = new FileInputStream(new File(digitalSinatureFolderName+"//"+fileName+".txt"));
			//File privateKeyFile = new File(keysFolderName+"//"+fileName+".key");
			//FileInputStream inputStreamPrivateKey = new FileInputStream(privateKeyFile);
			//byte[] privateKeyBytes = new byte[(int) privateKeyFile.length()];
			//inputStreamPrivateKey.read(privateKeyBytes);
			//inputStreamPrivateKey.close();
			
			File publicKeyFile = new File(keysFolderName+"//"+fileName+".pub");
			FileInputStream inputStreamPublicKey = new FileInputStream(publicKeyFile);
			byte[] publicKeyBytes = new byte[(int) publicKeyFile.length()];
			inputStreamPublicKey.read(publicKeyBytes);
			inputStreamPublicKey.close();
			//DigitalSignature ds=new DigitalSignature(publicKeyBytes,privateKeyBytes);
                        DigitalSignature ds=new DigitalSignature(publicKeyBytes);
			//int numberOfFiles=Integer.parseInt(keys_br.readLine());
			FileOutputStream outputStream = new FileOutputStream(new File(decryptionFolderName+"//"+fileName+fileExtension));
			EncryptionDecryptionAES enc=new EncryptionDecryptionAES();
                        
                        
			for(int i=1;i<=numberOfFiles;i++){
				String fileNameSplit=encryptionFolderName+"//"+fileName+"_"+i+fileExtension;
				File inputFile = new File(fileNameSplit);
				FileInputStream inputStream = new FileInputStream(inputFile);
				byte[] inputBytes = new byte[(int) inputFile.length()];
				inputStream.read(inputBytes);
				inputStream.close();			
				byte[] outputBytes=enc.getCipher(inputBytes,secretKeyList,i-1,ds_br,ds);
                                if(outputBytes.length==0){
                                    outputStream.close();
                                    return -1;
                                }
				outputStream.write(outputBytes);				
			}
			outputStream.close();
                        return numberOfFiles;
		}
                return -1;
    }
    
    
    
}