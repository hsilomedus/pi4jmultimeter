
package mk.hsilomedus.multimeter;


public class ReadValues {
  public int value;
  public int[] bands = new int[20];
  
  @Override
  public String toString() {
    StringBuilder bld = new StringBuilder();
    bld.append("{\"v\":");
    bld.append(value);
    bld.append(",\"d\":[");
    for (int i = 0; i < bands.length; i++) {
      if (i > 0) {
        bld.append(',');
      }
      bld.append(bands[i]);
    }
    //Add some padding to avoid buffering and network lag.
    bld.append("],\"extraPadding\":\"dkfjgshdf dkf hsdkf hsdkf hsdkfhjgksdjg sdfg sdf gsdfg sdfgsdfgsdfgsdfgoiioihoih" +
    		"lldksfjglskfdlg ksdjf lsdfgsldf jsdfgisdfogijdsofgi jsdofgi sdofigjdsofi jgsodif jodsif gsdfg o" +
    		"osdfigsdfg sfdg sdfgsdfg sfdg sdf gsdfg sdf gsdf gsdfg sdf sdf gsfd sdfg sd fgsdgfsdf gsdfg sd gf" +
    		"sdfgsdf sfd sdf gsdf gsdfg sfdg sdfg sfd gsfdg sdfg dsf gsdf sdfg sdfg sdf gsdfg sdfg sdfg sdg" +
    		"sdfg sdfgsdfg sdfgsd gfsdf gsdf gdsf gsdf gsd fgsdfgsdf gsdf ksdjf lsdfgsldf jsdfgisdfogijdsofgi jsdofgi sdofigjdsofi jgsodif jodsif gsdfg o" +
                "osdfigsdfg sfdg sdfgsdfg sfdg sdf gsdfg sdf gsdf gsdfg sdf sdf gsfd sdfg sd fgsdgfsdf gsdfg sd gf" +
                "sdfgsdf sfd sdf gsdf gsdfg sfdg sdfg sfd gsfdg sdfg dsf gsdf sdfg sdfg sdf gsdfg sdfg sdfg sdg" +
                "sdfg sdfgsdfg sdfgsd gfsdf gsdf gdsf gsdf gsd fgsdfgsdf gsdfg\"}");
    return bld.toString();
  }
}
