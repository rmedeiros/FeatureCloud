#!/usr/sfw/bin/python
import sys, os, re, string, time, random
from math import *


#=====================#
# TREECLOUD FUNCTIONS #
#=====================#

#####################################################
# Copyright 2008-2014 Philippe Gambette
# 
# This file is part of TreeCloud v1.4.2beta (15/04/2014).
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



# Return the input string where all punctuation signs have been replaced by spaces.
def removePunct(line):
        new_string = ''
        for char in line:
                if char=="_":
                    new_string = new_string+char
                else :
                    if char in string.punctuation:
                        new_string = new_string+" "
                    else :
                        new_string = new_string+char
        return new_string



# Return the input string where all punctuation signs have been replaced by spaces.
def doubleAntislash(line):
        new_string = ''
        for char in line:
                if char=='\\':
                        new_string = new_string+"\\\\"
                else :
                        new_string = new_string+char
        return new_string



# Open a text file and put into a 2-element table:
# -> a table containing the sequence of all words in the text
# -> a dict containing all distinct words of the text with associated nb of occurrences
# * filename: string
# * sepchar: string, used to separate cooccurrence windows, will not be added to wordlist
def openText(filename,sepchar):
        fd = open(filename,"r")
        lines = fd.readlines()

        wordlist={}
        text=[]
        i=0
        
        #------------------------------
        #go through the text to extract the words, store them in dict "wordlist" with frequencies
        #and in table "text" in the order they appear
        #------------------------------
        for line in lines:
                remaining = line;
                remaining = removePunct(remaining);
                while len(remaining)>2 :
                        word=""
                        #res=re.search("^[ '.,:;?!()-]*([a-zA-Z0-9]+)([ '.,:;?!()-].*)",remaining)
                        res=re.search("^[ ]*([^ ]+)(.*)",remaining)
                        if res:
                                remaining=res.group(2)
                                word=res.group(1).lower()
                                res=re.search("(.*)\n",word)
                                if res: 
                                        word=res.group(1)
                                        #print "Treating word '"+word+"'."
                        else:
                                #print "Not treating '"+remaining+"'."
                                res=re.search("^[ '.,:;?!()-]*([a-zA-Z0-9]+)",remaining)
                                if res:
                                        remaining=""
                                        word=res.group(1).lower()
                                else:
                                        remaining=""
                                        word=""
                        if word!="":
                                #print i,word
                                text.append(word)
                                if word!=sepchar:
                                        i+=1
                                        if wordlist.has_key(word):
                                                wordlist[word]+=1
                                        else:
                                                wordlist[word]=1
                                        #print word,wordlist[word]
        fd.close()
        results=[]
        results.append(text)
        results.append(wordlist)
        return results



# Open a distance matrix stored in a csv file and put into a 2-element table:
# -> names is a string list
# -> matrix is a real matrix
# * filename: string
def openMatrix(filename):
        fd = open(filename,"r")
        lines = fd.readlines()
        matrix=[]
        names=[]
        i=0
        for line in lines:
          if i>0:
             j=0
             res=re.search("([^;]*);(.*)",line)
             matrixline=[]
             while res:
                   line=res.group(2)
                   if j>0:
                        matrixline.append(res.group(1))
                   else:
                        names.append(res.group(1))
                   j+=1
                   res=re.search("([^;]*);(.*)",line)
             matrixline.append(line)
             matrix.append(matrixline)
          i+=1
          
        #Transform strings to reals
        realmatrix=[]
        for i in range(0,len(matrix)):
          matrixrow=matrix[i]
          realmatrixrow=[]
          for j in range(0,len(matrixrow)):
             if matrix[i][j]=='':
                 realmatrixrow.append(float(matrix[j][i]))
             else :
                 realmatrixrow.append(float(matrix[i][j]))
          realmatrix.append(realmatrixrow)

        fd.close()
        result=[]
        result.append(names)
        result.append(realmatrix)
        return result


# Open a dict stored in a csv file and returns a dict
# * filename: string
def openDict(filename):
        fd = open(filename,"r")
        lines = fd.readlines()
        result={}
        for line in lines:
             res=re.search("([^;]*);(.*)",line.lower())
             if res:
                   result[res.group(1)]=res.group(2)
        fd.close()
        return result


# Open a dict stored in a csv file and returns a dict
# * filename: string
def createConcordance(text,filename,left,right):
        fd = open(filename,"r")
        output = open(filename+"conc.txt","w")
        lines = fd.readlines()
        result={}
        mode=1;
        
        if mode==1:
        # for each word in the file, find its concordances, one after the other
           for line in lines:
             res=re.search("(.*$)",line.lower())
             if res:
                   word=res.group(1)
                   print "Looking for -"+word+"-"
                   for i in range(0,len(text)):
                       if text[i]==word:
                            chaine=""
                            j=i-left
                            if j<0:
                                j=0
                            while (j<i+right+1) and (j<len(text)):
                                chaine=chaine+" "+text[j]
                                j=j+1
                            print chaine
                            output.writelines(chaine+" aaaaa\n")
        else:
        # find the concordances in the text of all words, following their order of appearance
           allWords=[]
           for line in lines:
             res=re.search("(.*$)",line.lower())
             if res:
                   word=res.group(1)
                   allWords.append(word)
                   
           for i in range(0,len(text)):
             for word in allWords:
                       if text[i]==word:
                            chaine=""
                            j=i-left
                            if j<0:
                                j=0
                            while (j<i+right+1) and (j<len(text)):
                                chaine=chaine+" "+text[j]
                                j=j+1
                            print chaine
                            output.writelines(chaine+" aaaaa\n")
        fd.close()
        output.close()
        return result

# Returns a dict freqs, which associates for any n (in decreasing order):
# a dict of all words which appeared n times
def sortByFrequency(wordlist):
        freqs={}
        words=wordlist.keys()
        words.sort()
        for word in words:
            val=wordlist[word]
            if freqs.has_key(val):
                freqs[val][word]=1
            else :
                freqs[val]={}
                freqs[val][word]=1
        return freqs



# Returns the stoplist words in a table:
def loadStoplist(stoplistfile):
        stoplist={}
        if os.path.isfile(stoplistfile):
                fd = open(stoplistfile,"r")
                lines = fd.readlines()
                for line in lines:
                        res=re.search("(.*)\n",line.lower())
                        if res:
                                stoplist[(res.group(1).rstrip('\n\r'))]=1
                fd.close()
        return stoplist



# Save into a csv file (word;nb of occurrences) ordered by decreasing frequency:
# * freqs is a dict which associates for any n: a dict of all words which appeared n times (in decreasing order)
# * stoplist is a table containing loaded stopwords
# * filename is a string
def saveFrequencies(freqs,stoplist,filename):
        frequencies=freqs.keys()
        frequencies.sort()
        j=len(frequencies)-1
        freqoutput = open(filename,"w")
        
        while j>=0:
                theseWords=freqs[frequencies[j]].keys()
                k=0
                while k<len(theseWords):
                        if not(stoplist.has_key(theseWords[k])):
                                freqoutput.writelines(theseWords[k]+";"+str(frequencies[j])+"\n")
                        k+=1
                j+=-1        
        freqoutput.close();



# Map every kept word to an integer
# Return a table of 3 elements
# -> a dict which associates an integer to each word
# -> a table which associates a word to each integer between 0 and the nb of kept words -1
# -> a table which associates the nb of occurrences of the corresponding word to each integer...
# * freqs is a dict which associates for any n: a dict of all words which appeared n times (in decreasing order)
# * stoplist is a table containing loaded stopwords
# * minnb is the minimum number of occurrences of the words in the treecloud.
#   -1 if not set
# * nbwords is the minimum number of words in the treecloud.
#   -1 if not set, 30 if not set and minnb not set
# * sepchar contains a string with a special character used to separate sliding windows (alternative to winsize)
def wordList(freqs,stoplist,minnb,nbwords,sepchar):
        theResult=[]
        keptWordsId={}
        keptWords=[]
        keptWordsFrequencies=[]
        frequencies=freqs.keys()
        frequencies.sort()
        j=len(frequencies)-1
        i=0
        while (j>=0) and (frequencies[j]>=minnb):
                theseWords=freqs[frequencies[j]].keys()
                k=0
                while k<len(theseWords):
                        if (theseWords[k]!=sepchar) and (not(stoplist.has_key(theseWords[k])) and (i<nbwords)):
                                keptWordsId[theseWords[k]]=i
                                keptWordsFrequencies.append(frequencies[j])
                                #print theseWords[k]
                                keptWords.append(theseWords[k])
                                i+=1
                        #else :
                                #print theseWords[k],"not kept!"
                        k+=1
                j+=-1
        theResult.append(keptWordsId)
        theResult.append(keptWords)
        theResult.append(keptWordsFrequencies)
        #print keptWords
        return theResult



# With an imposed list of kept words, map every kept word to an integer
# Return a table of 3 elements
# -> a dict which associates an integer to each word
# -> a table which associates a word to each integer between 0 and the nb of kept words -1
# -> a table which associates the nb of occurrences of the corresponding word to each integer...
# * freqs is a dict which associates for any n: a dict of all words which appeared n times (in decreasing order)
# * thekeptwords is a dict of all kept words
# * sepchar contains a string with a special character used to separate sliding windows (alternative to winsize)
def imposedWordList(freqs,thekeptwords,sepchar):
        theResult=[]
        keptWordsId={}
        keptWords=[]
        keptWordsFrequencies=[]
        frequencies=freqs.keys()
        frequencies.sort()
        j=len(frequencies)-1
        i=0
        while j>=0:
                theseWords=freqs[frequencies[j]].keys()
                k=0
                while k<len(theseWords):
                        #print theseWords[k]
                        if (theseWords[k]!=sepchar) and thekeptwords.has_key(theseWords[k]):
                                keptWordsId[theseWords[k]]=i
                                keptWordsFrequencies.append(frequencies[j])
                                #print theseWords[k],i,frequencies[j]
                                keptWords.append(theseWords[k])
                                i+=1
                                #print "kept"
                        #else :
                                #print theseWords[k],"not kept!"
                        k+=1
                j+=-1
        theResult.append(keptWordsId)
        theResult.append(keptWords)
        theResult.append(keptWordsFrequencies)
        return theResult



# Transforms into "" the elements of table text
# which are not present as keys in dict keptWordsId
# * text contains the text stored in a table (each word in a cell)
# * keptWordsId is a dict which associates an integer to each kept word
# * sepchar contains a string with a special character used to separate sliding windows (alternative to winsize)
def filterText(text,keptWordsId,sepchar):
        i=0
        while i<len(text):
                if not(keptWordsId.has_key(text[i])):
                        if text[i]!=sepchar:
                               text[i]=""
                i+=1
        return text



# Alter text, by deleting words with probabililty proba
# text: table of strings
# proba: real number
def alterText(text,proba):
        i=0
        alteredText=[]
        while i<len(text):
                if random.random()>proba:
                        alteredText.append(text[i])
                i+=1
        return alteredText



# Alter text, by deleting x percent of randmoly chosen blocks
# text: table of strings
# percent: integer < 100
def alterBlocks(text,percent):
        i=0
        alteredText=[]
        blockNumbers=random.sample(xrange(0,99,1), int(floor(percent)))
        next=-0.0000001
        while i<len(text):
                if i>next:
                        next+=len(text)/100
                        blockNumber=int(floor(i*100.0/len(text)))
                        selectedBlock=1
                        for j in blockNumbers:
                                 if blockNumber==j:
                                         selectedBlock=0
                        if selectedBlock==0:
                                 i=int(floor(i+len(text)/100))
                alteredText.append(text[i])
                i+=1
        return alteredText



# Computes the cooccurrence matrix using sliding windows
# -> returns coocc, a matrix of tables of 4 reals:
#    coocc[j][k][0] is the number of windows containing word j and word k
#    coocc[j][k][1] is the number of windows not containing word j but containing word k
#    coocc[j][k][2] is the number of windows containing word j but not word k
#    coocc[j][k][3] is the number of windows containing neither word j nor word k
# * text contains the filtered text (the only words are kept words) stored in a table
# * keptWordsId is a dict which associates an integer to each kept word
# * winSize is the size of the sliding window
# * step is the sliding step of the sliding window
def computeCooccurrence(text,keptWordsId,winSize,step):
        #initialize variables
        coocc=[]
        freqWin=[]
        i=0
        while i<len(keptWordsId):
                coocRow=[]
                freqWin.append(0)
                j=0 
                while j<len(keptWordsId):
                        coocCase=[]
                        coocCase.append(0);
                        coocCase.append(0);
                        coocCase.append(0);
                        coocCase.append(0);
                        coocRow.append(coocCase)
                        j+=1
                coocc.append(coocRow)
                i+=1
                
        window = {}
        i=0
        previousPercent=0
        while i<len(text)+winSize :
                if (100*i/(len(text)+winSize))>previousPercent+5:
                        previousPercent+=5
                        #print previousPercent,'%'
                #------------------------------
                #update the content of the sliding window:
                #------------------------------
                if i<len(text):
                        #------------------------------
                        #the end of sliding window has not reached the end of the text yet
                        #------------------------------
                        if text[i]!="":
                                if window.has_key(text[i]):
                                        window[text[i]]+=1
                                else:
                                        window[text[i]]=1
                if i>=winSize:
                #------------------------------
                #the beginning of the sliding window has not reached the beginning of the text yet
                #------------------------------
                        if text[i-winSize]!="":
                                window[text[i-winSize]]+=-1
                                if window[text[i-winSize]]==0 :
                                        del window[text[i-winSize]]

                #------------------------------
                #update the cooccurrence matrix by using all words in the window
                #------------------------------
				# window currently contains all the words in the window, as well as their number of occurrences in the window                
                if i % step==0:
                        windowWords=window.keys();
                        j=0;
                        while j<len(windowWords):
                                k=j+1;
                                posj=keptWordsId[windowWords[j]]
                                if window[windowWords[j]]>0:
                                        #print windowWords[j],freqWin[posj],keptWordsFrequencies[posj]
										#this window contains the j-th word                                        
                                        freqWin[posj]+=1

                                while k<len(windowWords):
                                        posk=keptWordsId[windowWords[k]]
                                        coocc[posj][posk][0]+=min(1,window[windowWords[j]])*min(1,window[windowWords[k]])
                                        coocc[posk][posj][0]=coocc[posj][posk][0]
                                        k+=1;
                                j+=1
                i+=1;
        j=0
        while j<len(freqWin):
                k=0;
                while k<len(freqWin):
                        coocc[j][k][1]=freqWin[j]-coocc[j][k][0]
                        coocc[k][j][2]=coocc[j][k][1]
                        coocc[k][j][1]=freqWin[k]-coocc[k][j][0]
                        coocc[j][k][2]=coocc[k][j][1]
                        coocc[j][k][3]=len(text)+winSize-coocc[j][k][0]-coocc[j][k][1]-coocc[j][k][2]
                        coocc[k][j][3]=coocc[j][k][3]
                        k+=1;
                j+=1
        return coocc



# Computes the cooccurrence matrix according to text windows separated by a special character
# -> returns coocc, a matrix of tables of 4 reals:
#    coocc[j][k][0] is the number of windows containing word j and word k
#    coocc[j][k][1] is the number of windows not containing word j but containing word k
#    coocc[j][k][2] is the number of windows containing word j but not word k
#    coocc[j][k][3] is the number of windows containing neither word j nor word k
# * text contains the filtered text (the only words are kept words) stored in a table
# * keptWordsId is a dict which associates an integer to each kept word
# * sepchar contains a string with a special character used to separate sliding windows (alternative to winsize)
def computeCooccurrenceDisjoint(text,keptWordsId,sepchar):
        #initialize variables
        coocc=[]
        freqWin=[]
        winnb=0
        i=0
        while i<len(keptWordsId):
                coocRow=[]
                freqWin.append(0)
                j=0 
                while j<len(keptWordsId):
                        coocCase=[]
                        coocCase.append(0);
                        coocCase.append(0);
                        coocCase.append(0);
                        coocCase.append(0);
                        coocRow.append(coocCase)
                        j+=1
                coocc.append(coocRow)
                i+=1
                
        window = {}
        i=0
        previousPercent=0
        while i<len(text) :
                if (100*i/len(text))>previousPercent+5:
                        previousPercent+=5
                        #print previousPercent,'%'
                #------------------------------
                #update the content of the sliding window:
                #------------------------------
                if text[i]!=sepchar:
                        if text[i]!="":
                                window[text[i]]=1
                else:
                       #------------------------------
                       #update the cooccurrence matrix by using all words in the window
                       #------------------------------
                        winnb+=1
                        windowWords=window.keys();
                        #print winnb,"cooccurrence windows found.",len(windowWords),i
                        j=0;
                        while j<len(windowWords):
                                k=j+1;
                                posj=keptWordsId[windowWords[j]]
                                if window[windowWords[j]]>0:
                                        #print windowWords[j],freqWin[posj],keptWordsFrequencies[posj]
                                        freqWin[posj]+=1

                                while k<len(windowWords):
                                        posk=keptWordsId[windowWords[k]]
                                        coocc[posj][posk][0]+=1
                                        coocc[posk][posj][0]=coocc[posj][posk][0]
                                        k+=1;
                                j+=1
                        window = {}
                i+=1;
        if text[i-1]!=sepchar:
                        winnb+=1
                        windowWords=window.keys();
                        j=0;
                        while j<len(windowWords):
                                k=j+1;
                                posj=keptWordsId[windowWords[j]]
                                if window[windowWords[j]]>0:
                                        freqWin[posj]+=1

                                while k<len(windowWords):
                                        posk=keptWordsId[windowWords[k]]
                                        coocc[posj][posk][0]+=1
                                        coocc[posk][posj][0]=coocc[posj][posk][0]
                                        k+=1;
                                j+=1

        j=0
        while j<len(freqWin):
                k=0;
                while k<len(freqWin):
                        coocc[j][k][1]=freqWin[j]-coocc[j][k][0]
                        coocc[k][j][2]=coocc[j][k][1]
                        coocc[k][j][1]=freqWin[k]-coocc[k][j][0]
                        coocc[j][k][2]=coocc[k][j][1]
                        coocc[j][k][3]=winnb-coocc[j][k][0]-coocc[j][k][1]-coocc[j][k][2]
                        coocc[k][j][3]=coocc[j][k][3]
                        k+=1;
                j+=1
        return coocc



# Computes the distance matrix from the cooccurrence matrix
# -> returns a real matrix
# * coocc contains a cooccurrence matrix where cooc[i][j] is
#   in fact a table of 4 reals: O11, O12, O21, O22
#   in the formalism by Evert (2005)
# * formula contains a string, the name of the formula to apply
def distanceFromCooccurrence(coocc,formula):
        distance=[]
        j=0
        while j<len(coocc[0]):
                k=0
                distancerow=[]
                while k<len(coocc[0]):
                        if k==j :
                                distancerow.append(0)
                        else :
                                O11=coocc[j][k][0]
                                O12=coocc[j][k][1]
                                O21=coocc[j][k][2]
                                O22=coocc[j][k][3]
                                R1=O11+O12
                                R2=O21+O22
                                C1=O11+O21
                                C2=O12+O22
                                N=R1+R2
                                E11=R1*C1*1.0/N
                                E12=R1*C2*1.0/N
                                E21=R2*C1*1.0/N
                                E22=R2*C2*1.0/N
                                if formula=="chisquared":
                                        if R1*R2*C1*C2>0:
                                                distancerow.append(1000-(1.0*N*(O11*O22-O12*O21)*(O11*O22-O12*O21)/(R1*R2*C1*C2)))
                                        else:
                                                distancerow.append(0)
                                if formula=="mi":
                                        distancerow.append(log(1.0*max(0.0000000000001,O11)/max(0.0000000000001,E11)))
                                if formula=="liddell":
                                        if C1*C2>0:
                                                distancerow.append(1-(1.0*O11*O22-O12*O21)/(C1*C2))
                                        else :
                                                distancerow.append(0)
                                if formula=="dice":
                                        distancerow.append(1-2.0*O11/max(0.00000000001,(R1+C1)))
                                if formula=="jaccard":
                                        distancerow.append(1-1.0*O11/max(0.00000000001,(O11+O12+O21)))
                                if formula=="gmean":
                                        distancerow.append(1-1.0*O11/(max(0.00000000001,sqrt(R1*C1))))
                                if formula=="hyperlex":
                                        distancerow.append(1-max(1.0*O11/max(0.00000000001,C1),1.0*O11/max(0.00000000001,R1)))
                                if formula=="ms":
                                        distancerow.append(1-min(1.0*O11/max(0.00000000001,C1),1.0*O11/max(0.00000000001,R1)))
                                if formula=="oddsratio":
                                        distancerow.append(1-log((max(0.00000000001,O11*O22/(max(0.00000000001,O12*O21))))))
                                if formula=="zscore":
                                        distancerow.append(1-(O11-E11)*1.0/max(0.00000000001,sqrt(E11)))
                                if formula=="loglikelihood":
                                        distancerow.append(1-(O11*log(max(0.00000000001,O11)*1.0/max(0.00000000001,E11)))
                                        -(O12*log(max(0.00000000001,O12)*1.0/max(0.00000000001,E12)))
                                        -(O21*log(max(0.00000000001,O21)*1.0/max(0.00000000001,E21)))
                                        -(O22*log(max(0.00000000001,O22)*1.0/max(0.00000000001,E22)))
                                        )
                                if formula=="poissonstirling":
                                        distancerow.append(1-O11*(log(max(O11,0.00000001))-log(max(E11,0.00000001))-1))
                                if formula=="ngd":
                                        distancerow.append((max(log(max(R1,0.00000001)),log(max(C1,0.00000001)))-log(max(O11,0.00000001)))/(N-min(log(max(R1,0.00000001)),log(max(C1,0.00000001)))))

                        k+=1;
                distance.append(distancerow)
                j+=1
        return distance


        
# Normalize the distance matrix from the cooccurrence matrix
# -> returns a real matrix
# * mat contains a real matrix
# * mode is a string to choose the normalization mode: "linear" or "affine"
#   or "auto": apply affine only if the matrix contains a negative number.
def normalizeMatrix(mat,mode):
        themax=1
        themin=mat[0][0]
        for i in range(0,len(mat[0])):
                for j in range(0,len(mat[0])):
                        themax=max(themax,mat[i][j])
                        themin=min(themin,mat[i][j])
        if mode=="auto":
                if themin<0:
                        mode="affine"
                else:
                        mode="linear"
        #print mode,themax
        for i in range(0,len(mat[0])):
                for j in range(0,len(mat[0])):
                        if mode=="linear":
                                 mat[i][j]=((mat[i][j])*1.0)/themax
                        else:
                                 if mode=="log":
                                          mat[i][j]=log(1+99*(mat[i][j]-themin)*1.0/(themax-themin))
                                 else:         
                                          coeff=2.0/(2*(log(len(mat[0])))+1)
                                          #coeff=0.1
                                          mat[i][j]=coeff+(1-coeff)*(mat[i][j]-themin)*1.0/(themax-themin)
        return mat
        

# Computes the discrete and continuous arboricity of a dissimilarity matrix
# -> returns the result in a list
# * distance contains a real matrix
def computeArboricity(distance):
        theResult=[]
        nbQuad=0
        nbCorrectQuad=0
        sum=0
        for x in range(0, len(distance[0])):
                for y in range(x+1, len(distance[0])):
                        for z in range(y+1, len(distance[0])):
                                for t in range(z+1, len(distance[0])):
                                        fourPointDistances=[]
                                        fourPointDistances.append(distance[x][y]+distance[z][t])
                                        fourPointDistances.append(distance[x][z]+distance[y][t])
                                        fourPointDistances.append(distance[x][t]+distance[y][z])
                                        fourPointDistances.sort()
                                        Smin=fourPointDistances[0]
                                        Smed=fourPointDistances[1]
                                        Smax=fourPointDistances[2]
                                        nbQuad+=1
                                        if Smax-Smin==0:
                                                sum+=0
                                        else :
                                                sum+=(Smed-Smin)/(Smax-Smin)
                                        if Smax-Smed<Smed-Smin:
                                                nbCorrectQuad+=1
        theResult.append(100*nbCorrectQuad/nbQuad)
        theResult.append(100*sum/nbQuad)
        return theResult
        


# Export a matrix labeled by a list into a csv file
# * distance contains a real matrix
# * keptWords contains a string list
# * exportfilename contains a string
def exportToCsv(distance,keptWords):
        exportfilename = "exportCsv.csv"
        csvoutput = open(exportfilename,"w")
        j=0
        while j<len(keptWords):
                csvoutput.writelines(";"+keptWords[j])
                j+=1
        csvoutput.writelines("\n")
        j=0
        while j<len(keptWords):
                k=0;
                csvoutput.writelines(keptWords[j])
                while k<len(keptWords):
                        csvoutput.writelines(";"+str(distance[j][k]))
                        k+=1;
                j+=1
                csvoutput.writelines("\n")
        csvoutput.close();
        return exportfilename



# Export a matrix labeled by a list into a csv file
# * matrix contains a real matrix
# * exportfilename contains a string
def saveMatrixToCsv(matrix,exportfilename):
        csvoutput = open(exportfilename,"w")
        j=0
        while j<len(matrix):
                row=matrix[j]
                j+=1
                i=0
                while i<len(row):
                        csvoutput.writelines(str(row[i])+";")
                        i+=1
                csvoutput.writelines("\n")
        csvoutput.close();



# Export a matrix labeled by a list into a nexus file
# * distance contains a real matrix
# * keptWords contains a string list
# * exportfilename contains a string
# * unit equals 1 if the edges of the treecloud have the same length, 0 otherwise
def exportToNexus(distance,keptWords,exportfilename,unit):
        nexusoutput = open(exportfilename+".nexus","w")
        nexusoutput.writelines("#nexus\n")
        nexusoutput.writelines("\n")
        nexusoutput.writelines("BEGIN Taxa;\n")
        nexusoutput.writelines("DIMENSIONS ntax="+str(len(keptWords))+";\n")
        nexusoutput.writelines("TAXLABELS\n")
        j=0
        while j<len(keptWords):
                nexusoutput.writelines("["+str(j+1)+"] '"+keptWords[j]+"'\n")
                j+=1
        nexusoutput.writelines(";\n")
        nexusoutput.writelines("END; [Taxa]\n")
        nexusoutput.writelines("\n")
        nexusoutput.writelines("BEGIN Distances;\n")
        nexusoutput.writelines("DIMENSIONS ntax="+str(len(keptWords))+";\n")
        nexusoutput.writelines("FORMAT labels=left diagonal triangle=both;\n")
        nexusoutput.writelines("MATRIX\n")
        j=0
        while j<len(keptWords):
                k=0;
                nexusoutput.writelines("["+str(j+1)+"] '"+keptWords[j]+"'        ")
                while k<len(keptWords):
                        nexusoutput.writelines(" "+str(distance[j][k]))
                        k+=1;
                j+=1
                nexusoutput.writelines("\n")
                
        nexusoutput.writelines(";\n")
        nexusoutput.writelines("END; [Distances]\n")        
        nexusoutput.writelines(" \n");
        nexusoutput.writelines("BEGIN st_Assumptions;\n");
        nexusoutput.writelines("	disttransform=NJ;\n");
        #nexusoutput.writelines("	disttransform=NeighborNet Variance = OrdinaryLeastSquares Threshold = 1.0E-6;\n");
        nexusoutput.writelines("	treestransform=TreeSelector;\n");
        if unit==1:
                nexusoutput.writelines("	splitstransform=EqualAngle UseWeights=false RunConvexHull=true DaylightIterations=0 OptimizeBoxesIterations=5 SpringEmbedderIterations=0;\n");
        else:
                nexusoutput.writelines("	splitstransform=EqualAngle UseWeights=true  RunConvexHull=true DaylightIterations=0 OptimizeBoxesIterations=5 SpringEmbedderIterations=0;\n");
        nexusoutput.writelines("	SplitsPostProcess filter=dimension value=4;\n");
        nexusoutput.writelines("	autolayoutnodelabels;\n");
        nexusoutput.writelines("END; [st_Assumptions]\n");
        nexusoutput.close();



# Export a matrix labeled by a list into a nexus file
# * distance contains a real matrix
# * keptWords contains a string list
# * exportfilename contains a string
# * invisible = 1 if invisible mode, 0 otherwise
def nexusOrders(distance,keptWords,exportfilename,invisible):
        nexusoutput = open(exportfilename+".nexorders","w")
        nexusoutput.writelines("#nexus\n")
        nexusoutput.writelines("\n")
        nexusoutput.writelines("BEGIN Taxa;\n")
        nexusoutput.writelines("DIMENSIONS ntax="+str(len(keptWords))+";\n")
        nexusoutput.writelines("TAXLABELS\n")
        j=0
        while j<len(keptWords):
                nexusoutput.writelines("["+str(j+1)+"] '"+keptWords[j]+"'\n")
                j+=1
        nexusoutput.writelines(";\n")
        nexusoutput.writelines("END; [Taxa]\n")
        nexusoutput.writelines("\n")
        nexusoutput.writelines("BEGIN Distances;\n")
        nexusoutput.writelines("DIMENSIONS ntax="+str(len(keptWords))+";\n")
        nexusoutput.writelines("FORMAT labels=left diagonal triangle=both;\n")
        nexusoutput.writelines("MATRIX\n")
        j=0
        while j<len(keptWords):
                k=0;
                nexusoutput.writelines("["+str(j+1)+"] '"+keptWords[j]+"'        ")
                while k<len(keptWords):
                        nexusoutput.writelines(" "+str(distance[j][k]))
                        k+=1;
                j+=1
                nexusoutput.writelines("\n")
                
        nexusoutput.writelines(";\n")
        nexusoutput.writelines("END; [Distances]\n")        
        nexusoutput.writelines("\n")
        nexusoutput.writelines("BEGIN Splitstree;\n")
        nexusoutput.writelines("	EXECUTE FILE="+doubleAntislash(exportfilename)+".nexus;\n")
        nexusoutput.writelines("	SAVE FILE="+doubleAntislash(exportfilename)+".nocol.nexus REPLACE=yes;\n")
        if invisible==1 :
                nexusoutput.writelines("  QUIT;\n")
        nexusoutput.writelines("END;\n")
        nexusoutput.close()



# return the splits of the tree contained in the nexus file:
# -> {0,1} matrix where the columns are the leaves (word ids)
#                       the rows are the splits
# * keptWords contains a string list
# * filename is a string
def splitsFromNexus(keptWords,filename):
        fd = open(filename+".nocol.nexus","r")
        lines = fd.readlines()
        treeline=0
        splits=[]
        for line in lines:
                if treeline==0:
                        res=re.search(".*BEGIN Trees.*",line)
                        if res:
                                treeline=1
                else:
                        treeline=0
                        res=re.search("[^(] ([(].*[)]);.*",line)
                        if res:
                                splits=splitsFromNewick(res.group(1),keptWords)
        fd.close()
        return splits



# return the union split of two splits
# * split1 and split2 are two tables
def splitUnion(split1,split2):
        union=[]
        for i in range(0,len(split1)):
                union.append(max(split1[i],split2[i]))
        return union



# return 1 if the splits are equal, 0 otherwise
# * splits1 and splits2 are two tables with the same number of columns
def splitEqual(split1,split2):
        opposite=0
        if split1[0]!=split2[0]:
                opposite=1
        i=1
        while (i<len(split1)) and (opposite==abs(split1[i]-split2[i])):
                i+=1
        if i<len(split1):
                return 0
        else:
                return 1
        


# return the line number in splits2 where split1 has been found, -1 otherwise
# * split1 is a table which represents a split
# * splits2 is a table which represents a set of splits with the same number of columns as split1
def findSplit(split1,splits2):
        found=-1
        i=0
        while i<len(splits2) and found<0:
                if splitEqual(split1,splits2[i]):
                       found=i
                i+=1               
        return found
        
        
        
# return the size of the split = the size of one of the two separated clusters
# * split1 is a table which represents a split
def splitSize(split):
        i=0
        size=0
        while i<len(split):
                if split[i]==1:
                       size+=1
                i+=1               
        return size
        
        
        
# return the Robinson-Foulds similarity between splits1 and splits2
# (percentage of non trivial splits common to splits1 and splits2)
# * splits1 and splits2 are two tables with the same number of columns
def RFsimilarity(splits1,splits2):
        percentage=0
        nonTrivialSplits=0
        for i in range(0,len(splits1)):
                splitLen=splitSize(splits1[i])
                if splitLen>1 and splitLen<len(splits1[i])-1:
                        nonTrivialSplits+=1
                        if findSplit(splits1[i],splits2)>=0:
                                percentage+=1
        return percentage*1.0/nonTrivialSplits



# return the splits of the tree contained in the newick string:
# -> {0,1} matrix where the columns are the leaves (word ids)
#                       the rows are the splits
# * string is a newick string
# * keptWords contains a string list
def splitsFromNewick(string,keptWords):
        splits=[]
        nbwords=len(keptWords.keys())
        if string[0]=="(":
                subtrees=findSubtrees(string)
                nbSubtree=0
                currentSplit=[]
                for subtree in subtrees:
                        foundSplits=splitsFromNewick(subtree,keptWords)
                        nbSubtree+=1
                        if nbSubtree==1:
                                currentSplit=foundSplits[len(foundSplits)-1]
                        else:
                                currentSplit=splitUnion(currentSplit,foundSplits[len(foundSplits)-1])
                        for split in foundSplits :
                                splits.append(split)
                splits.append(currentSplit)
        else :
                res=re.search("([^:]+):.*",string)
                split=[]
                if res: 
                        if keptWords.has_key(res.group(1)):
                                wordid=keptWords[res.group(1)]
                                for i in range(0,nbwords):
                                        if i==wordid:
                                                split.append(1)
                                        else:
                                                split.append(0)
                                splits.append(split)
        return splits



# return a table containing strings which are the subtrees of the input
# * string is a newick string
def findSubtrees(string):
        subtrees=[]
        mode=0
        currentSubtree=""
        nbopen=0
        for char in string:
        # mode:
        # 0: initialization, 1: finding subtree, 2: stop
             addchar=1;
             if mode==1:
                    if char=="(":
                           nbopen+=1
                    if char==")":
                           nbopen=nbopen-1
                           if nbopen<0:
                                  subtrees.append(currentSubtree)
                                  mode=2
                    if char==",":
                           if nbopen==0:
                                  subtrees.append(currentSubtree)
                                  currentSubtree=""
                                  addchar=0
                    if addchar==1:
                                  currentSubtree=currentSubtree+char
             if mode==0:
                   mode=1
             
        return subtrees



# return nexus font and color associated to the level
def levelToFont(level,color):
        if color=="yahoo":
                levels=["'Arial-PLAIN-8' lc=223 223 223",
        "'Arial-PLAIN-9' lc=184 201 214",
        "'Arial-PLAIN-10' lc=102 138 168",
        "'Arial-PLAIN-11' lc=71 123 123",
        "'Arial-PLAIN-12' lc=231 101 0",
        "'Arial-PLAIN-13' lc=231 98 0",
        "'Arial-PLAIN-14' lc=231 98 0",
        "'Arial-PLAIN-15' lc=231 98 0",
        "'Arial-PLAIN-16' lc=231 98 0",
        "'Arial-PLAIN-17' lc=231 98 0",
        "'Arial-PLAIN-18' lc=255 51 0"]
        if color=="berry":
                levels=["'Arial-PLAIN-8' lc=220 220 220",
        "'Arial-PLAIN-9' lc=200 200 200",
        "'Arial-PLAIN-10' lc=180 180 180",
        "'Arial-PLAIN-11' lc=160 160 160",
        "'Arial-PLAIN-12' lc=140 140 140",
        "'Arial-PLAIN-13' lc=120 120 120",
        "'Arial-PLAIN-14' lc=70 70 70",
        "'Arial-PLAIN-15' lc=50 50 50",
        "'Arial-PLAIN-16' lc=30 30 30",
        "'Arial-PLAIN-17' lc=20 20 20",
        "'Arial-PLAIN-18' lc=0 0 0"]
        
        return levels[level]
        
        
        
# Compute the average position
# -> returns a table which contains:
#  -> a "position" dict which associates to each kept word its average position
#  -> a "dispersion" dict which associates to each kept word the standard deviation of its position
# * text contains the filtered text (the only words are kept words) stored in a table
# * keptWordsId is a dict which associates an integer to each kept word
def computeAveragePositions(text,keptWordsId):
        numberfound={}
        result=[]
        positions={}
        dispersion={}
        for i in range(0,len(text)):
             word=text[i]
             if word!="":
                  if numberfound.has_key(word):
                       nbfound=numberfound[word]
                       positions[word]=(positions[word]*nbfound+i*255.0/len(text))/(nbfound+1)
                       numberfound[word]=nbfound+1
                  else:
                       numberfound[word]=1
                       positions[word]=i*255.0/len(text)

        #positions contains numbers between 0 and 255
        #we now compute the standard deviation of these positions
        numberfound2={}
        for i in range(0,len(text)):
             word=text[i]
             if word!="":
                  if numberfound2.has_key(word):
                       nbfound=numberfound2[word]
                       difference=(i*255.0/len(text)-positions[word])
                       dispersion[word]=(dispersion[word]*nbfound+difference*difference)/(nbfound+1)
                       #dispersion[word]=(dispersion[word]*nbfound+difference*difference*1.0/numberfound[word])/(nbfound+1)
                       numberfound2[word]=nbfound+1
                  else:
                       numberfound2[word]=1
                       difference=(i*255.0/len(text)-positions[word])
                       dispersion[word]=difference*difference
                       #dispersion[word]=difference*difference*1.0/numberfound[word]

        #now we normalize the positions
        theminp=256.0
        themaxp=0.0
        themind=256*256
        themaxd=0.0
        words=positions.keys()
        
        for items in words:
             themaxp=max(themaxp,positions[items])
             theminp=min(theminp,positions[items])
             themaxd=max(themaxd,dispersion[items])
             themind=min(themind,dispersion[items])
             
        themaxd=sqrt(themaxd)
        themind=sqrt(themind)
        words=positions.keys()
        if themaxp>theminp:
             for items in words:
                  positions[items]=((positions[items])-theminp)*255.0/(themaxp-theminp)
        if themaxd>themind:
             #print "Normalizing Intensity",themind,themaxd
             for items in words:
                  dispersion[items]=(sqrt(dispersion[items])-themind)*100.0/(themaxd-themind)
        result.append(positions)
        result.append(dispersion)
        return result
                       
                       
# Compute colors according to cooccurrence with a target word
# * theword is the target word of the cooccurrence color computation
# * keptWordsId is a dict which associates an integer to each word
# * text contains the filtered text (the only words are kept words) stored in a table
# * distance contains a cooccurrence distance matrix
def computeCooccurrenceColors(theword,text,keptWordsId,distance):
        result=[]
        positions={}
        dispersion={}
        themin=distance[0][1]
        themax=themin
        
        keptWords=keptWordsId.keys();
        for word in keptWords:
                  if not(positions.has_key(word)):
                       value=distance[keptWordsId[theword]][keptWordsId[word]]
                       positions[word]=value
                       themax=max(value,themax)
                       if theword!=word:
                               themin=min(value,themin)
                       #print word,value,themin,themax
        items=positions.items()
        backitems=[ [v[1],v[0]] for v in items]
        backitems.sort()
        words= [ backitems[i][1] for i in range(0,len(backitems))]        
        
        i=0
        for word in words:
             i+=1
             if word!=theword:
                    #positions[word]=int(floor(127*(i/len(words)+(positions[word]-themin)/(themax-themin))))
                    positions[word]=int(floor(255*i/len(words)))
             else:
                    positions[word]=0
             dispersion[word]=0
        result.append(positions)
        result.append(dispersion)
        return result

                       

# return nexus font and color associated to the level
def onlyLevelToFont(level):
        levels=["'Arial-PLAIN-8'",
        "'Arial-PLAIN-9'",
        "'Arial-PLAIN-10'",
        "'Arial-PLAIN-11'",
        "'Arial-PLAIN-12'",
        "'Arial-PLAIN-13'",
        "'Arial-PLAIN-14'",
        "'Arial-PLAIN-15'",
        "'Arial-PLAIN-16'",
        "'Arial-PLAIN-17'",
        "'Arial-PLAIN-18'"]
        return levels[level]



# color the input nexus file
# * keptWordsId is a dict which associates an integer to each word
# * keptWordsFrequencies is a table which associates the nb of occurrences of the corresponding word to each integer...
# * filename is a string
# * text contains the filtered text (the only words are kept words) stored in a table
# * color contains the name of the coloring method (chronology, dispersion, yahoo, wordfoo, etc.)
# * equal to " " by default, otherwise contains the filename of a CSV font color file (see help for details)
# * equal to " " by default, otherwise contains the filename of a CSV font size file (see help for details)
# * distance contains a cooccurrence distance matrix
# * dendropath contains the path of Dendroscope if this program should be used to visualize the tree
def colorNexus(keptWordsId,keptWordsFrequencies,filename,text,color,customcolor,customsize,distance,dendropath):
        edgecolor="204 204 255"
        if color=="berry":
                edgecolor="230 230 230"
        colorok=0
        sizeok=0
                
        if os.path.isfile(customcolor):
                colorok=1
                customColorDic=openDict(customcolor)
        if os.path.isfile(customsize):
                sizeok=1
                customSizeDic=openDict(customsize)
                
        chronology=0
        if color=="chronology":
                chronology=1
        if color=="chronodisp":
                chronology=2
        if color=="dispersion":
                chronology=3
        if chronology>0:
                edgecolor="204 204 204"                               
                locations=computeAveragePositions(text,keptWordsId)
                positions=locations[0]
                dispersion=locations[1]
                
        res=re.search("word(.*)",color)
        if res:
                chronology=1
                locations=computeCooccurrenceColors(res.group(1),text,keptWordsId,distance)
                positions=locations[0]
                dispersion=locations[1]

        if dendropath=="":
                nexusoutput = open(filename+".colored.nexus","w")
        else :
                nexusoutput = open(filename+".colored.dendrorders","w")
        fd = open(filename+".nocol.nexus","r")
        lines = fd.readlines()
        themin=keptWordsFrequencies[0]
        themax=keptWordsFrequencies[0]
        for element in keptWordsFrequencies:
                if element<themin:
                        themin=element
                if element>themax:
                        themax=element
                        
                        
        for line in lines:
                res=re.search("([0-9]+ '(.*)'.*) f=.*",line)
                if res:
                        if chronology>0:
                                if chronology==1:
                                       averagepos=str(max(0,int(floor(positions[res.group(2)]))))
                                       averagepos2=str(max(0,int(floor(255-positions[res.group(2)]))))
                                if chronology==2:
                                       averagepos=str(max(0,int(floor(positions[(res.group(2))]*(100-0.8*dispersion[res.group(2)])/100.0))))
                                       averagepos2=str(max(0,int(floor((255-positions[(res.group(2))])*(100-0.8*dispersion[res.group(2)])/100.0))))
                                if chronology==3:
                                       averagepos=str(max(0,int(floor(dispersion[res.group(2)]*255.0/100))))
                                       averagepos2=str(max(0,int(floor(255-dispersion[res.group(2)]*255.0/100))))

                                if dendropath=="":
                                       #blue/red colors
                                       nexusoutput.writelines(res.group(1)+" f="+onlyLevelToFont(int(1+9.99999*(log(keptWordsFrequencies[keptWordsId[res.group(2)]])-log(themin))/(log(themax)-log(themin))))+" lc="+averagepos2+" "+"0"+" "+averagepos+",\n")
                                       #inverted gray level
                                       #nexusoutput.writelines(res.group(1)+" f="+onlyLevelToFont(int(1+9.99999*(log(keptWordsFrequencies[keptWordsId[res.group(2)]])-log(themin))/(log(themax)-log(themin))))+" lc="+str(int(floor((200*(255-int(averagepos2)))/255)))+" "+str(int(floor((200*(255-int(averagepos2)))/255)))+" "+str(int(floor((200*(255-int(averagepos2)))/255)))+",\n")
                                       #gray level
                                       #nexusoutput.writelines(res.group(1)+" f="+onlyLevelToFont(int(1+9.99999*(log(keptWordsFrequencies[keptWordsId[res.group(2)]])-log(themin))/(log(themax)-log(themin))))+" lc="+str(int(floor((200*int(averagepos2))/255)))+" "+str(int(floor((200*int(averagepos2))/255)))+" "+str(int(floor((200*int(averagepos2))/255)))+",\n")
                                else :
                                       nexusoutput.writelines("deselect nodes;\n");
                                       nexusoutput.writelines("select taxa="+res.group(2)+";\n")
                                       #colored chronology
                                       nexusoutput.writelines("set labelcolor="+averagepos2+" "+"0"+" "+averagepos+";\n")
                                       #gray level chronology
                                       #nexusoutput.writelines("set labelcolor="+averagepos2+" "+"0"+" "+averagepos2+";\n")
                                       nexusoutput.writelines("set font="+onlyLevelToFont(int(1+9.99999*(log(keptWordsFrequencies[keptWordsId[res.group(2)]])-log(themin))/(log(themax)-log(themin))))+";\n")

                        else:
                                fontpluscolor=re.search("([^ ]+) lc=(.*)",levelToFont(int(1+9.99999*(log(keptWordsFrequencies[keptWordsId[res.group(2)]])-log(themin))/(log(themax)-log(themin))),color))
                                if fontpluscolor:
                                       chosenSize=fontpluscolor.group(1)
                                       chosenColor=fontpluscolor.group(2)
                                       if colorok==1:
                                              if customColorDic.has_key(res.group(2)):
                                                      chosenColor=customColorDic[res.group(2)]
                                       if sizeok==1:
                                              if customSizeDic.has_key(res.group(2)):
                                                      chosenSize=customSizeDic[res.group(2)]
                                              
                                       if dendropath=="":
                                              nexusoutput.writelines(res.group(1)+" f="+chosenSize+" lc="+chosenColor+",\n")
                                       else :
                                              nexusoutput.writelines("deselect nodes;\n");
                                              nexusoutput.writelines("select taxa="+res.group(2)+";\n")
                                              nexusoutput.writelines("set labelcolor="+chosenColor+";\n")
                                              nexusoutput.writelines("set font="+chosenSize+";\n")
                                              
                else:
                        if dendropath=="":
                                res=re.search("([0-9]+ [0-9]+ [0-9]+ s=[0-9]+ w=[^ ,]+),",line)
                                if res:
                                        nexusoutput.writelines(res.group(1)+" fg="+edgecolor+",\n")
                                else:
                                        res=re.search("(.*)bg=0 0 0(.*,)",line)
                                        if res:
                                                nexusoutput.writelines(res.group(1)+" s=n bg=0 0 0"+res.group(2)+"\n")
                                        else:
                                                nexusoutput.writelines(line)
                        else:
                                res=re.search("\[1\] tree[^=]+=[^ ]+ (.*)",line)
                                if res:
                                        nexusoutput.writelines("add tree="+res.group(1)+"\n");  
                                        
                        
        fd.close()
        if dendropath=="":
                nexusoutput.writelines("BEGIN Splitstree;\n");
                nexusoutput.writelines("	EXPORTGRAPHICS FILE="+doubleAntislash(filename)+".pdf TITLE=bidule FORMAT=pdf TEXTASSHAPES=yes REPLACE=yes;\n");
                nexusoutput.writelines("END;\n");
        else:
                nexusoutput.writelines("deselect nodes;\n select edges;\n set color="+edgecolor+";\n deselect all;\n")
                nexusoutput.writelines("set drawer=RadialCladogram;\n")
        nexusoutput.close();
        
        
        
# Computes a distance between two real matrices
def matrixDistance(distance1,distance2):
        dist=0
        for i in range(0, len(distance1[0])):
                for j in range(0, len(distance1[0])):
                        if not(i==j):
                                diff=distance2[i][j]-distance1[i][j]
                                dist+=diff*diff
        return sqrt(dist*1.0/(len(distance1[0])*(len(distance1[0])-1)))
        
        