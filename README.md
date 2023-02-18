# Secure-File-Sharing-Using-Hybrid-Cryptography
Secure File Storage and Sharing System

- Developed an end-to-end encrypted secure file storage system that allows users to securely store and share files with other users
- Implemented a client-server architecture using Java programming language, with support for chatting and real-time file sharing between users
- Used different public key cryptography algorithms like RSA and Symmetric key cryptography techniques like AES, along with hashing algorithms like SHA to ensure integrity of messages
- Divided files into chunks and encrypted them using AES algorithm, and generated a digital signature for each file chunk, along with a metadata file consisting of secret keys and information about file chunks
- Stored files securely on a remote server and used a trusted center for distribution of public keys
- Developed a user interface using Java Swings, with a login interface and a file-sharing application interface that included features like download button, file event, connect event, disconnect event, and chat event
- Ensured integrity and authentication along with confidentiality, in contrast to existing systems that only focus on confidentiality and use stenography to share secret keys between users
- Enhanced the security of public cloud by making it more secure and can be modified to meet the confidentiality and integrity needs of different projects
