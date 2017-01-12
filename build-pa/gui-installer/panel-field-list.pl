#!/usr/bin/perl

while (my $line = <>)
{
	chomp($line);
	if ($line =~ /panel order="(\d+)"/)
	{
		print "\nPanel $1";
	}
	if ($line =~ /<field.*variable="([^"]+)"/)
	{
		print "\n\t$1 ";
	}
	if ($line =~ /<spec.*set="([^"]+)"/)
	{
		print "($1)";
	}
	if ($line =~ /<variable.*name="([^"]+)"\s+value="([^"]+)"/)
	{
		print "\n\t\t$1 ($2)";
	}
}
print "\n"
