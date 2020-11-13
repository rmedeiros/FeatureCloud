#!/usr/sfw/bin/python
#"C:\Program Files\OpenOffice.org 2.4\program\python.bat" C:\Sites\gambettelirmm\TreecloudDistribution\treecloud.py stoplist=C:\Sites\GambetteLirmm\TreecloudDistribution\StoplistEnglish.txt nbwords=30 distance=hyperlex unit=1 color=chronology C:\TreeCloud\BBC1.txt
#"C:\Program Files\OpenOffice.org 2.4\program\python.bat" C:\Sites\gambettelirmm\TreecloudDistribution\treecloud.py words=C:\Treecloud\Racine\words.csv nbwords=30 distance=hyperlex unit=1 color=chronology C:\TreeCloud\Racine\Phedre.txt

#####################################################
# Copyright 2008-2014 Philippe Gambette
# 
# This file is part of TreeCloud v1.4beta (18/01/2014).
#
# TreeCloud is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# TreeCloud is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with TreeCloud.  If not, see <http://www.gnu.org/licenses/>.
# 
# For more information:
# http://www.treecloud.org
#####################################################


import sys, os, re, string, time
from TreecloudFunctions import *
from math import *
import dendropy



#------------------------------
# analyze and load parameters
#------------------------------
args={}
i=1;
word_file=open("temp2.txt","r")
wordlist = word_file.readlines()
word_file.close()
thefile="temp2.txt";


if not(args.has_key("minnb")):
        args["minnb"]=-1
else :
        args["minnb"]=int(args["minnb"])
minnb=args["minnb"];
nbwords=200;

if minnb<1:
        minnb=1


winSize=2;

if not(args.has_key("step")):
        args["step"]="1"
step=int(args["step"]);


theformula="oddsratio";


if not(args.has_key("unit")):
        args["unit"]="1"
unit=int(args["unit"]);

if not(args.has_key("normat")):
        args["normat"]="auto"
normat=args["normat"];

if not(args.has_key("color")):
        args["color"]="chronology"
color=args["color"];


sepchar="";

if not(args.has_key("splitstreepath")):
        args["splitstreepath"]="C:\TreeCloud\SplitsTree.lnk"
splitstreepath=args["splitstreepath"];

if not(args.has_key("customcolor")):
        args["customcolor"]=" "
customcolor=args["customcolor"];

if not(args.has_key("customsize")):
        args["customsize"]=" "
customsize=args["customsize"];

if not(args.has_key("dendropath")):
        args["dendropath"]=""
dendropath=args["dendropath"];


# Build the tree cloud
# * thefile contains the address of the text file
# * winSize is the size of the sliding window
# * step is the sliding step of the sliding window
# * minnb is the minimum number of occurrences of the words in the treecloud.
#   -1 if not set
# * nbwords is the minimum number of words in the treecloud.
#   -1 if not set, 30 if not set and minnb not set
# * formula contains the name of the cooccurrence distance formula to apply
# * normat contains a string for the normalization method to transform
#   the distance matrix into a [0,1] matrix (affine,linear,log,auto)
# * unit equals 1 if the edges of the treecloud have the same length, 0 otherwise
# * color is a string, name of the color set (yahoo,berry,chronology...)
# * splitstreepath contains the path of the program SplitsTree used to draw the tree cloud
# * sepchar contains a string with a special character used to separate sliding windows (alternative to winsize)
# * dendropath contains the path of Dendroscope if used instead of SplitsTree to draw the tree cloud
def buildTreeCloud(thefile,minnb,nbwords,winSize,step,formula,normat,unit,color,splitstreepath,dendropath,sepchar):
        textPlusWordlist=openText(thefile,sepchar)
        text=textPlusWordlist[0]
        wordlist=textPlusWordlist[1]

        #saveTenWordsPerLine(thefile)
        #separateLines(thefile)

        #------------------------------
        # wordlist now contains all words of the text as keys
        # whose associated values are nb of occurrences
        #------------------------------

        freqs=sortByFrequency(wordlist)
        #------------------------------
        # freqs is a dict which associates for any n: a dict of all words which appeared n times (in decreasing order)
        #------------------------------

        #------------------------------
        # load stoplist
        #------------------------------
        if args.has_key("stoplist"):
                stoplist=loadStoplist(args["stoplist"])
        else :
                stoplist={}



        #------------------------------
        # compute list of most frequent words
        #------------------------------
        if nbwords<1:
                nbwords=len(text)
        if args.has_key("words"):
                # if a word list is imposed, keep only words inside it
                theResult=imposedWordList(freqs,loadStoplist(args["words"]),sepchar)
        else :
                theResult=wordList(freqs,stoplist,minnb,nbwords,'aaaa')
        keptWordsId=theResult[0]
        keptWords=theResult[1]
        keptWordsFrequencies=theResult[2]


        #------------------------------
        # keptWords now associates all kept words to their id (integer)
        # keptWordsId is the reverse map (associates integer to word)
        #------------------------------

        #------------------------------
        # filter text: remove all words not in keptWords from table "text"
        #------------------------------
        text = filterText(text,keptWordsId,sepchar)
        #print text

        #------------------------------
        # compute cooccurrence matrix from filtered text:
        #------------------------------

        if sepchar=="":
                coocc = computeCooccurrence(text,keptWordsId,winSize,step)
        else :
                coocc = computeCooccurrenceDisjoint(text,keptWordsId,sepchar)
        #print coocc

        #------------------------------
        # compute distance matrix from cooccurrence matrices:
        #------------------------------
        distance = normalizeMatrix(distanceFromCooccurrence(coocc,formula),normat)
        if normat=="log":
                distance = normalizeMatrix(distance,"linear")
        #------------------------------
        # compute arboricity of the distance matrix:
        #------------------------------
        #print "Computing arboricity..."
        #arboricity=computeArboricity(distance)
        #print "Discrete arboricity:",arboricity[0];
        #print "Continuous arboricity:",arboricity[1];

        #------------------------------
        # transform distance matrix into CSV File:
        #------------------------------

        fileName = exportToCsv(distance,keptWords)
        pdm = dendropy.PhylogeneticDistanceMatrix.from_csv(
                src=open(fileName),
                delimiter=";")
        nj_tree = pdm.nj_tree()
        print(nj_tree.as_string("newick"))
        os.remove(fileName)




buildTreeCloud(thefile,minnb,nbwords,winSize,step,theformula,normat,unit,color,splitstreepath,dendropath,sepchar)

                

