# Documenting the process

## Update 1

I followed https://cs.nyu.edu/~kapp/cs101/processing_on_the_web/ to converted the Java Processing program into what I suppose may be possible to run as a JS sketch, and I have been successful running it in the Processing IDE.
But I fail to make an html document to run a sketch, not even hello-world examples from the website. From what I understand, many browsers don't allow local files to be read when opening an html document locally. I will have to look into localhost.

## Update 2

It seems that running the files on localhost (python3 -m http.server) solves the issue of not loading the .pde files. I can get the test sketches to run but my program fails. Seems to be something wrong with the cloth.solve() call, possibly that the contraints are placed in ArrayList which is a Java datastructure. Initial problems with canvas size was corrected by placing the call to size() inside the setup function. I will look into JS Arrays next.
