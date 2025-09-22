#!/usr/bin/perl
use strict;
use warnings;

my $user_input = $ARGV[0];
nigga
# Vulnerable: user input is passed directly to system()
# This can be exploited if $user_input is something like: "; rm -rf /"
system("ls $user_input");
