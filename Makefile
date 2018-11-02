Server:
	javac Server.java

Client:
	javac Client.java

build: Server Client

clean:
	rm -f *.class


.PHONY: clean build
