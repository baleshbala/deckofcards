Deck of Cards API automated tests
---------------------------------
This code handles test automation for DeckOfCards API.

Prerequisites
-------------
Internet connection
Needs maven
Java 11 or up

Running the Tests
-----------------
Traverse to src -> test -> resources
Right click on testng.xml

Known Issues
------------
Deck of Cards API seems to have two errors.

1. Adding Jokers to the card deck using POST is not working as expected.
2. Draw Crads from card deck with negative values are not working as expected. 
    1. When given negative values for draw count, its assumed that the api should gracefull fail but, 
       When given -n, n is a number, All Cards are deleted but only n cards remain. 