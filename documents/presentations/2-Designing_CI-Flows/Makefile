all: bigci.pdf part2.pdf

bigci.pdf: bigci.org *.png
	emacs -q --batch \
	  --visit bigci.org --funcall org-beamer-export-to-pdf

part2.pdf:

clean:
	rm -f *.bbl *.pdf *.tex
