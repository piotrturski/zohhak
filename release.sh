mvn versions:set
# -DnewVersion=1.0.2
mvn clean verify source:jar javadoc:jar gpg:sign deploy
mvn versions:revert
