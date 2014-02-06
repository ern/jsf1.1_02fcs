#
# The contents of this file are subject to the terms
# of the Common Development and Distribution License
# (the License). You may not use this file except in
# compliance with the License.
# 
# You can obtain a copy of the License at
# https://javaserverfaces.dev.java.net/CDDL.html or
# legal/CDDLv1.0.txt. 
# See the License for the specific language governing
# permission and limitations under the License.
# 
# When distributing Covered Code, include this CDDL
# Header Notice in each file and include the License file
# at legal/CDDLv1.0.txt.    
# If applicable, add the following below the CDDL Header,
# with the fields enclosed by brackets [] replaced by
# your own identifying information:
# "Portions Copyrighted [year] [name of copyright owner]"
# 
# [Name of File] [ver.__] [Date]
# 
# Copyright 2006 Sun Microsystems Inc. All Rights Reserved
#

$appName = "generate-savebegin";
$tempName = "$appName" . "file";

require "getopts.pl";

&Getopts('o:j:lh');

if ((!$opt_j) || (!$opt_o) || $opt_h) {
    print "Usage: \n";
    print "$appName -o outputMethods-script -j jarfile [-l]\n";
    print "\t For each method in each class in jarfile, generate \n";
    print "\t JCov -savebegin=.  Use outputMethods-script to do so.\n";
    print "\t options:\n";
    print "\t          -l means output each option on a line by itself.\n\n";

    exit(0);
}

open(METHODS, "perl $opt_o -j $opt_j|");
@methods = <METHODS>;
close(METHODS);
$line = "";

foreach $_ (@methods) {
  if ($opt_l) {
    print "-savebegin=$_";
  }
  else {
    chop;
    if (/</) {
      $line = "$line" . "\"-savebegin=$_\" ";
    }
    else {
      $line = "$line" . "-savebegin=$_ ";
    }
  }
}

if (!($opt_l)) {
  print "jcov.savepoints=$line\n";
}
