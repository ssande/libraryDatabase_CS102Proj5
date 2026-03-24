Project requirements:
- Database should implement a dynamic binary search tree of dynamic linked lists to organize
  the info within the database
- Program should validate input whenever possible and catch all exceptions
- Following operations should be implemented:
  Add     - include an option to add a new patron as well as an option to add new checkout info
  Print   - should print the contents of the database sorted by patron name and checkout info
  Search  - using a binary search algorithm, the program should search for a specific Patron
            given the patron's ID number or search for details of checked out book using the
            ISBN number of the book.
  Store   - allows the user to write the entire contents of the database to a disk. This option
            should prompt the user for the names of the two files in the current directory to be
            used for writing the desired info. Prog should then write all records to those files
            in the same format used for input
  Load    - allows the user to re-initialize the database. This option should prompt the user for
            two file names. If both files exist, prog will delete the current database and create
            a new database based upon the files.
- Include a graphical user interface to your library database program. You may use any of the
  standard JavaFX UI components and use the JavaFX Scene Builder tool to design your app.

(May have missed some requirements as this was an evolving project that required different
implementations as the project grew)
