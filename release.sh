mvn versions:use-latest-releases


mvn versions:set 
# -DnewVersion=1.0.2
mvn verify source:jar javadoc:jar gpg:sign deploy
mvn versions:revert
