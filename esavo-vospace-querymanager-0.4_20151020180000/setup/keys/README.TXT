The following steps must be followed for creating new keys in DER format (the one used for RSA):

    * Creating a public/private key pair (in PEM format) with a length of 2048 bits.

      openssl genrsa -out dest_file_to_be_protected.pem 2048

    * For creating the corresponding private key in DER format.

      openssl pkcs8 -topk8 -nocrypt -in dest_file_to_be_protected.pem -inform PEM -out dest_file_to_be_protected.der -outform DER

    * For creating the corresponding public key in DER format.

      openssl rsa -inform PEM -in dest_file_to_be_protected.pem -outform DER -pubout -out dest_file_public.der

