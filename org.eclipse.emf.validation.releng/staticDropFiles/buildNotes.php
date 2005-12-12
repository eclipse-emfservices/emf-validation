<?php
  $pre = "../../../../scripts/";
  
  $parts = explode("/", getcwd());
  $parts2 = explode("-", $parts[count($parts) - 1]);
  $buildVer = $parts[count($parts) - 2];
  $buildName = $parts[count($parts) - 1];

  preg_match("/(.+)\/([A-Z])(\d+)\/buildNotes\.php$/",$PHP_SELF,$m);

  $HTMLTitle = "Eclipse Tools - UML2 $buildVer - Build Notes for Build $buildName";

  $noHeader=true;
  include $pre."includes/header.php";

  //$buildVer = "1.0.0";
  $buildNotesFile = $CVSpreUML2."downloads/drops/".$buildVer."/".$buildName.".html";
  ini_set("display_errors","0"); // suppress file not found errors
  $f = file($buildNotesFile);
  ini_set("display_errors","1"); // and turn 'em back on.
  if (!$f) { ?>
    <p><b><font face="Verdana" size="+3">Build Notes</font></b></p>

    <table border=0 cellspacing=5 cellpadding=2 width="100%">
      <tr>
        <td align=LEFT valign=TOP colspan="3" bgcolor="#0080C0">
          <b>
            <font color="#FFFFFF" face="Arial,Helvetica">
              Build Notes for <?php echo "Build $buildName"; ?>
            </font>
          </b>
        </td>
      </tr>
    </table>

    <p>No build notes found in <?php echo $buildNotesFile; ?>.</p>

<?php
  } else {
    foreach ($f as $line) {
      echo $line;
    }
  }
?>

<?php include $pre."includes/nav.php"; ?>

</body>
</html>
