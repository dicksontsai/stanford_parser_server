# Stanford Parser Server

A simple Tomcat servlet that parses sentences. You can run this on localhost (default port is 8080).

[Stanford NLP Local Extension](https://github.com/dicksontsai/stanford-nlp-local-extension)
is a companion project that implements a Chrome Extension to send web content to this server.
You can use this extension to get parse trees from highlighted text, for example.

## Setup

### Tomcat Setup

1. Download Tomcat from https://tomcat.apache.org/download-90.cgi
1. Add `$CATALINA_HOME` to your `.bashrc`/`.zshrc` pointing to the downloaded directory.
1. You may need to run `chmod +x $CATALINA_HOME/bin/*.sh` to execute those scripts.
1. Add manager roles to Tomcat by editing `$CATALINA_HOME/conf/tomcat-users.xml`.
   ```
   <role rolename="tomcat"/>
   <role rolename="manager-gui"/>
   <role rolename="manager-script"/>
   <user username="tc" password="123" roles="tomcat,manager-gui"/>
   <user username="tcs" password="456" roles="tomcat,manager-script"/>
   ```
1. Create a new `setenv.sh` file: `$CATALINA_HOME/bin/setenv.sh`.

   1. Locate your JDK installation. I found my MacOS' JDK in `ls /Library/java/ -> /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home`.
   1. Add `JAVA_HOME` to `setenv.sh`:
      ```
      JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home
      ```

1. Start the Tomcat server using `$CATALINA_HOME/bin/startup.sh`.

### Repo Setup

1. Download Apache Ant from https://ant.apache.org/bindownload.cgi
   1. Install and add to your path. You should now have `$ANT_HOME` defined.
1. Download Apache Ivy and copy the Ivy jar to `$ANT_HOME/lib`.
1. Copy the file "lib/catalina-ant.jar" from your Tomcat installation into the "lib" directory of your Ant installation.
1. Create a "build.properties" file in your application's top-level
   source directory (or your user login home directory) that defines
   appropriate values for the "manager.password", "manager.url", and
   "manager.username" properties described above. In our case:
   1. manager.username: `tcs`
   1. manager.password: `456`
   1. manager.url: Can leave blank. Defaults to `localhost:8080`.

### Stanford NLP Setup

The `ivy.xml` file should already take care of fetching Stanford NLP's CoreNLP library. However, you will also need
to download the parse model separately. Download the English model at https://nlp.stanford.edu/software/stanford-corenlp-4.0.0-models-english.jar
and copy it to the `lib` directory.

## Example Request

Currently, the only supported endpoint is `POST http://localhost:8080/parser/parse`.
* Input: sentence
* Output: JSON object containing a `tree` field.

```
$ curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "sentence=The quick brown fox jumps over the lazy dog" http://localhost:8080/parser/parse

{"tree":["(ROOT\n  (S\n    (NP (DT The) (JJ quick) (JJ brown) (NN fox))\n    (VP (VBZ jumps)\n      (PP (IN over)\n        (NP (DT the) (JJ lazy) (NN dog))))))\n"]}
```
