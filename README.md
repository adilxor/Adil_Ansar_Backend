How to setup

**Backend:**
Install Java:

`sudo apt-get install oracle-java8-installer`

Install Maven:

`sudo apt-get install maven`

**Deploy Backend:**

`cd <repository_full_path>/backend`

`mvn clean install -DskipTests`

`nohup java -Dserver.port=8080 -jar target/PZBackend.jar &`

**Frontend:**

Install NVM:

`curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash`

Source it to use nvm on current login

```
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
```
Install Node and npm:

`nvm install v10.15.1`

**Deploy Frontend:**

`cd <repository_full_path>/frontend`

`npm install`

`npm start`

Now go to `localhost:3000`



Demo Url:
http://pzapp.southindia.cloudapp.azure.com/

Code Coverage and Code Quality report
http://pzapp.southindia.cloudapp.azure.com/sonarqube/dashboard?id=com.prezi%3Abackend


**Things that can be made better given more time:**

1. Optimize title search by adding a caching layer and storing data in form of trie.
2. Refractor frontend code a bit.
4. Write unit test cases for frontend.
5. UX/UI can be improved a bit.
6. Allow user to chose page size and make pagination ux a bit better.
