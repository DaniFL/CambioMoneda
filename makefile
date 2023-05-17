compilar:limpiar
	mkdir bin
	find src -name *.java | xargs javac -cp bin -d bin

ejecutar:compilar
	java -cp bin aplicacion.Principal

limpiar:
	rm -rf bin

jar:compilar
	jar cvfm principal.jar Manifest.txt -C bin/ .

ver:jar
	java -jar principal.jar

javadoc:compilar
	find . -type f -name "*.java" | xargs javadoc -d html -encoding utf-8 -docencoding utf-8 -charset utf-8

