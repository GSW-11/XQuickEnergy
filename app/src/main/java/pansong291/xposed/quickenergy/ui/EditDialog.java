package pansong291.xposed.quickenergy.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import pansong291.xposed.quickenergy.util.Config;

public class EditDialog
{
 private static AlertDialog editDialog;
 private static EditText edt;
 public enum EditMode
 { TIME_INTERVAL, ADVANCE_TIME, COLLECT_INTERVAL, COLLECT_TIMEOUT,
  RETURN_WATER_30, RETURN_WATER_20, RETURN_WATER_10, RECEIVE_POINT_TIME }
 private static EditMode mode;

 public static void showEditDialog(Context c, CharSequence title, EditMode em)
 {
  mode = em;
  try
  {
   getEditDialog(c, title).show();
  }catch(Throwable t)
  {
   editDialog = null;
   getEditDialog(c, title).show();
  }
  editDialog.setTitle(title);
 }

 private static AlertDialog getEditDialog(Context c, CharSequence title)
 {
  if(editDialog == null)
  {
   edt = new EditText(c);
   editDialog = new AlertDialog.Builder(c)
    .setTitle(title)
    .setView(edt)
    .setPositiveButton(
    "确定",
    new OnClickListener()
    {
     Context context;

     public OnClickListener setData(Context c)
     {
      context = c;
      return this;
     }

     @Override
     public void onClick(DialogInterface p1, int p2)
     {
      try
      {
       int i = Integer.parseInt(edt.getText().toString());
       switch(mode)
       {
        case TIME_INTERVAL:
         if(i > 0)
         {
          Toast.makeText(context, "需要重启支付宝生效", 0).show();
          Config.setTimeInterval(i * 60_000);
         }
         break;

        case ADVANCE_TIME:
         if(i > 0)
          Config.setAdvanceTime(i);
         break;

        case COLLECT_INTERVAL:
         if(i > 0)
          Config.setCollectInterval(i);
         break;

        case COLLECT_TIMEOUT:
         if(i > 0)
          Config.setCollectTimeout(i * 1_000);
         break;

        case RETURN_WATER_30:
         if(i >= 0)
          Config.setReturnWater30(i);
         break;

        case RETURN_WATER_20:

         if(i >= 0)
          Config.setReturnWater20(i);
         break;

        case RETURN_WATER_10:
         if(i >= 0)
          Config.setReturnWater10(i);
         break;

        case RECEIVE_POINT_TIME:
         if(i >= 0 && i < 24)
          Config.setReceivePointTime(i);
         break;
       }
      }catch(Throwable t)
      {}
     }
    }.setData(c))
    .create();
  }
  String str = "";
  switch(mode)
  {
   case TIME_INTERVAL:
    str = String.valueOf(Config.timeInterval() / 60_000);
    break;

   case ADVANCE_TIME:
    str = String.valueOf(Config.advanceTime());
    break;

   case COLLECT_INTERVAL:
    str = String.valueOf(Config.collectInterval());
    break;

   case COLLECT_TIMEOUT:
    str = String.valueOf(Config.collectTimeout() / 1_000);
    break;

   case RETURN_WATER_30:
    str = String.valueOf(Config.returnWater30());
    break;

   case RETURN_WATER_20:
    str = String.valueOf(Config.returnWater20());
    break;

   case RETURN_WATER_10:
    str = String.valueOf(Config.returnWater10());
    break;

   case RECEIVE_POINT_TIME:
    str = String.valueOf(Config.receivePointTime());
    break;
  }
  edt.setText(str);
  return editDialog;
 }

}
