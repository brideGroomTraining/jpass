JPass
=====

Overview
--------
JPass is a simple, small, portable password manager application with strong encryption. It allows you to store user names, passwords, URLs and generic notes in an encrypted file protected by one master password.

Features:

* Strong encryption - AES-256-CBC algorithm (SHA-256 is used as password hash)
* Portable - single jar file which can be carried on a USB stick
* Built-in random password generator
* Organize all your user name, password, URL and notes information in one file
* Data import/export in XML format

![JPass](https://raw.githubusercontent.com/gaborbata/jpass/master/resources/jpass-capture.gif)

Usage
-----
Java 8 or later is recommended to run JPass. Most platforms have a mechanism to execute `.jar` files (e.g. double click the `jpass-0.1.15-SNAPSHOT.jar`).
You can also run the application from the command line by typing (the password file is optional):

    java -jar jpass-salt-0.1.15-SNAPSHOT-jar-with-dependencies.jar [password_file]


Download
--------
You can find the latest distribution package under the [releases](https://github.com/gaborbata/jpass/releases) link.

How to compile
--------------
* Maven: `mvn clean compile assembly:single -Dmaven.test.skip=true`

Configuration
-------------
Default configurations can be overridden in `jpass.properties` file:

| Configuration key                  | Value type | Default value |
| ---------------------------------- | ---------- | ------------- |
| system.look.and.feel.enabled       | boolean    | false         |
| clear.clipboard.on.exit.enabled    | boolean    | false         |
| default.password.generation.length | integer    | 14            |
| fetch.icons                        | boolean    | true          |

Salt installation
-----------------
This salt branch uses a stronger encryption technique than the master branch, such as sult. Due to this newly added scheme, the ``.jpass`` files produced with the master branch cannot be opened.

New ``.jpass`` contains following structure.
```
[36bytes:random salt][16bytes:CBC initial vector][rest:gzip binary]
*gzip binary: [encrypted gzip of xml (AES256 CBC mode)]
```

The 256 bit key to encrypt the xml file is being created with the following method.
```
SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
```

Due to this ``PBKDF2`` algorithm, it requires JRE of which version is greater than or equals to 1.8.

The salt encryptition makes it impossible to prepare rainbow tables. For example, look at the table of the pairs of a key and its SHA256 value.

| password  | value                                                            |
|:---------:|:----------------------------------------------------------------:|
| love      | 686f746a95b6f836d7d70567c302c3f9ebb5ee0def3d1220ee9d4e9f34f5e131 |
| pass      | d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1 |
| hash      | d04b98f48e8f8bcc15c6ae5ac050801cd6dcfd428fb5f9e65c4e16e7807340fa |

| password + random salt | value |
|:----------------------:|:-----:|
| love + ()U$"HF)NSAA... |unknown|
| pass + MREINiew9_iw... |unknown|

--Ryoji

File Encryption
---------------
![encrypt_file.png](./resources/encrypt_file.png)

The structure of the encrypted file ``.enc`` is the same of the ``.jpass`` file.


--Ryoji

RSA key 
--------
![rsa_key_pair.png](./resources/rsa_key_pair.png)

| Scheme                                | Key length |
|:-------------------------------------:|:----------:|
| RSA/ECB/OAEPWithSHA-256AndMGF1Padding |    4096    |

![rsa_key_pair.png](./resources/rsa_enc.png)

![rsa_key_pair.png](./resources/rsa_dec.png)

Note: Encrypted pass phrase becomes very long text.

|original phrase|encrypted phrase|
|:-------------:|:--------------:|
|dadada|TIMaLK0tyZE+2+kyj+I4AIdgt1sji/uNxoTz/ArFogjt6xFjRT6dmxSk2quZqG53XqmHYJQmYGUN/WapyhSzwu3Gr5tKCTx3cyqobZ+tciDgZsT0GYc2CLmCvSlWrwCJD5fG1Te5oSv6eJCW1gj6SgE3IJrIac2FTEZbKBThmTq36XfLEdXgfCZnuWl/GcUValZcOXOHHrNdijUzO5KFEfJu42mm8C6PBn6UWEpNqVwSDz9rQ9M75T2WbUpJttVR92l8O0iWk0GwFpIW+dvC8AD0Tyih96JXcPanU04LxJE7nFELMltRF+LMzWELuJWx0KT+HTPA0NG4J0/4Q9cQKPKG70b0LCn5rSJiJMZEn+1cTKVGpgbtNnnspqbTejyNe8YGNSx/1J38ORJhPT874OR7B75yM0QJO9zRtt1LpLGBkRr1KYJ8cbtkaZWYVDzrVuL81NLfL7dYqqoiiGNP8IGQ/M3LCh73YVMFnhUKFq7q+dGwrfZHDXHSuh1J/OA8JH4hSuhMUnvLi0D27DqfVG7b2HiL7+3tTR8QbFXyAumZtO6/FzQg5sOui5mGWxpp+KGvkxmdC4PLiw+Mc9fmdRA1WOM8ep3hTIxYZ4ykKkbZ9RAYrznsQTQIcGj1XDMxJ+gaRD4YW5RNNqJnH+ccZJaixaqF+h0BnyqXsKBnR80= (changes every time as using random)|


--Ryoji

License
-------
Copyright (c) 2009-2017 Gabor Bata

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

---

This software includes MicroCrypt 0.3, covered by the following license:

Copyright (c) 2007 Timm Knape

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
3. Neither the name of Timm Knape nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

---

This software includes SpringUtilities, covered by the following license:

Copyright (c) 1995-2008 Sun Microsystems, Inc.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
3. Neither the name of Sun Microsystems nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

---

This software uses icons from the [Silk icon set by Mark James](http://www.famfamfam.com/lab/icons/silk/).

The Silk icon set is licensed under a [Creative Commons Attribution 2.5 License](http://creativecommons.org/licenses/by/2.5/).

---

This software uses icons from the [Tango base icon theme](http://tango.freedesktop.org/Tango_Desktop_Project).

The Tango base icon theme is licensed under the [Creative Commons Attribution Share-Alike license](http://creativecommons.org/licenses/by-sa/2.5/).

The icon ``kgpg_key3.png`` was created by David Vignoni (GNU/GPL) https://commons.wikimedia.org/wiki/File:Kgpg_key3.png
