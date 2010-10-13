<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://java.sun.com/xml/ns/persistence"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.0" xmlns:p="http://java.sun.com/xml/ns/persistence" exclude-result-prefixes="p" >
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="p:properties">
		<properties>
			<xsl:apply-templates select="@* | *" />
			<test name="untest" value="mavaleur"/>
		</properties>
	</xsl:template>


</xsl:stylesheet>
