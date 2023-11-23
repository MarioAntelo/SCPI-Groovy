<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="urn:firabcn:Diggihub:buscaContrato"
                xmlns:ns0="http://schemas.datacontract.org/2004/07/DiggiHub_FIRA">
  <xsl:output method="xml" indent="yes"/>
  
 
   <xsl:template match="/ns0:ContractData">
        <xsl:element name="xs:ArrayOfContractData">
            <xsl:element name="{local-name()}">
                <xsl:apply-templates select="node()"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="/ns0:ArrayOfContractData">
        <xsl:element name="xs:ArrayOfContractData">
                 <xsl:apply-templates select="node()"/>
        </xsl:element>
    </xsl:template>
  
  <xsl:template match="*">
    <xsl:element name="{local-name()}">
      <xsl:apply-templates select="node()"/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>