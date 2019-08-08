#
GNAME= simple
GSRC= $(GNAME).g

all: classes grammar test

classes:
	javac -d . src/*.java

grammar: $(GSRCS)
	java org.antlr.Tool -fo . $(GSRC)

test:
	javac *.java

clean:
	rm *.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens
