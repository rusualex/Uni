<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/movies">
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
    <h2>A Movie Collection</h2>
            <table border="1">
                <xsl:for-each select="./movie">
                    <tr>
                        <td>
                            <xsl:value-of select="titles"/>
                        </td>
                        <td>
                            <xsl:value-of select="genre"/>
                        </td>
                        <xsl:for-each select="./actors">
                            <td>
                                <xsl:apply-templates/>
                            </td>
                        </xsl:for-each>
                        <td>
                            <xsl:value-of select="date"/>
                        </td>
                        <td>
                            <xsl:value-of select="duration"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
    </body>
</html>
</xsl:template>
</xsl:stylesheet>