package tech.yunjing.biconlife.jniplugin.db.daTable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 提醒功能对象
 * Created by sun.li on 2017/9/12.
 */
@Table("RemindFunctionObj")
public class RemindFunctionObj extends BaseEntityObj{

    /** 提醒ID*/
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int remindID;

    /** 对应闹钟ID*/
    @Column("alarmID")
    private String alarmID;

    /** 提醒功能标题*/
    @Column("remindTitle")
    private String remindTitle;

    /** 提醒功能开始时间*/
    @Column("remindStartTime")
    private String remindStartTime;

    /** 提醒功能重复时间(延时时间)*/
    @Column("remindRepetitionTime")
    private String remindRepetitionTime;

    /** 提醒功能重复日期*/
    @Column("remindRepetitionDate")
    private String remindRepetitionDate;

    /** 提醒功能是否开启,默认不开启false*/
    @Column("whetherOpen")
    private boolean whetherOpen = false;

    /** 是否为编辑状态*/
    private boolean whetherEdit = false;

    /** 更新时间*/
    @Column("updateDate")
    private String updateDate;

    public int getRemindID() { return remindID; }

    public void setRemindID(int remindID) { this.remindID = remindID; }

    public String getAlarmID() { return alarmID; }

    public void setAlarmID(String alarmID) { this.alarmID = alarmID; }

    public String getRemindTitle() { return remindTitle; }

    public void setRemindTitle(String remindTitle) { this.remindTitle = remindTitle; }

    public String getRemindStartTime() { return remindStartTime; }

    public void setRemindStartTime(String remindStartTime) { this.remindStartTime = remindStartTime; }

    public String getRemindRepetitionTime() { return remindRepetitionTime; }

    public void setRemindRepetitionTime(String remindRepetitionTime) { this.remindRepetitionTime = remindRepetitionTime; }

    public String getRemindRepetitionDate() { return remindRepetitionDate; }

    public void setRemindRepetitionDate(String remindRepetitionDate) { this.remindRepetitionDate = remindRepetitionDate; }

    public boolean isWhetherOpen() { return whetherOpen; }

    public void setWhetherOpen(boolean whetherOpen) { this.whetherOpen = whetherOpen; }

    public boolean isWhetherEdit() { return whetherEdit; }

    public void setWhetherEdit(boolean whetherEdit) { this.whetherEdit = whetherEdit; }

    public String getUpdateDate() { return updateDate; }

    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }

    public enum EnumClass {
        remindID,remindTitle, remindStartTime, remindRepetitionTime, remindRepetitionDate, whetherOpen, updateDate
    }
}
