package love.simbot.example.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.OnGroupAddRequest;
import love.forte.simbot.annotation.OnGroupMemberIncrease;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.*;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.api.sender.Setter;
import love.simbot.example.tools.Log_settler;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Beans
public class MyNewGroupMemberListen {
    @Depend
    private MessageContentBuilderFactory messageBuilderFactory;
    private static final Map<String, String> REQUEST_TEXT_MAP = new ConcurrentHashMap<>();
    @OnGroupAddRequest

    public void onRequest(GroupAddRequest groupAddRequest, Setter setter, Sender sender) throws IOException {
        AccountInfo accountInfo = groupAddRequest.getRequestAccountInfo();
        BotInfo botInfo = groupAddRequest.getBotInfo();
        if (!accountInfo.getAccountCode().equals(botInfo.getBotCode())) {
            String text = groupAddRequest.getText();
            if (text != null) {
                REQUEST_TEXT_MAP.put(accountInfo.getAccountCode(), text);
            }
            GroupInfo groupInfo = groupAddRequest.getGroupInfo();

            Log_settler.writelog(accountInfo.getAccountNickname()+"("+accountInfo.getAccountCode()+")"+"申请加入群"+groupInfo.getGroupName()+"("+groupInfo.getGroupCode()+")"+",申请备注："+text+"\n\n\n");
            setter.acceptGroupAddRequest(groupAddRequest.getFlag());
            MessageContentBuilder buil_=messageBuilderFactory.getMessageContentBuilder();
            MessageContent msgg=buil_
                    .text(accountInfo.getAccountNickname())
                    .text("(")
                    .text(accountInfo.getAccountCode())
                    .text(")")
                    .text("申请加入此群,加群信息为")
                    .text(text)
                    .build();
            sender.sendGroupMsg(groupAddRequest.getGroupInfo(),msgg);
        }
    }
 
    @OnGroupMemberIncrease
    public void newGroupMember(GroupMemberIncrease groupMemberIncrease, Sender sender) throws IOException {

        MessageContentBuilder builder = messageBuilderFactory.getMessageContentBuilder();
        AccountInfo accountInfo = groupMemberIncrease.getAccountInfo();
        MessageContent msg = builder
                .at(accountInfo)
                .text(" 欢迎萌新!我是机器人"+groupMemberIncrease.getBotInfo().getBotName()+" 很高兴见到你\n")
                .build();
        GroupInfo groupInfo = groupMemberIncrease.getGroupInfo();
        sender.sendGroupMsg(groupInfo, msg);
        Log_settler.writelog("OnGroup"+String.valueOf(groupInfo));
        Log_settler.writelog(String.valueOf(groupMemberIncrease.getBotInfo()));
        Log_settler.writelog(String.valueOf(msg)+"\n\n\n");
    }

    @Listen(GroupMemberPermissionChanged.class)
    public void permissionChange(GroupMemberPermissionChanged groupMemberPermissionChanged, Sender sender) throws IOException {
        AccountInfo accountInfoC=groupMemberPermissionChanged.getAccountInfo();
        AccountInfo operatorinfo=groupMemberPermissionChanged.getOperatorInfo();
        GroupInfo groupInfoC=groupMemberPermissionChanged.getGroupInfo();
        MessageContentBuilder builder1 = messageBuilderFactory.getMessageContentBuilder();
        boolean permissionlose=groupMemberPermissionChanged.isLostManagementRights();
        boolean permissionget=groupMemberPermissionChanged.isGetManagementRights();
        MessageContent message_;
        if (permissionlose){
            message_=builder1
                .at(accountInfoC)
                .text("的权限被")
                .at(operatorinfo)
                .text("降级为群成员权限")
                .build();
            sender.sendGroupMsg(groupInfoC, message_);
            Log_settler.writelog("OnGroup"+String.valueOf(groupInfoC));
            Log_settler.writelog(String.valueOf(groupMemberPermissionChanged.getBotInfo()));
            Log_settler.writelog(String.valueOf(message_)+"\n\n\n");
        }
        if (permissionget){
            message_=builder1
                .at(accountInfoC)
                .text("的权限被")
                .at(operatorinfo)
                .text("升级为管理权限")
                .build();
            sender.sendGroupMsg(groupInfoC, message_);
            Log_settler.writelog("OnGroup"+String.valueOf(groupInfoC));
            Log_settler.writelog(String.valueOf(groupMemberPermissionChanged.getBotInfo()));
            Log_settler.writelog(String.valueOf(message_)+"\n\n\n");
        }
        
    }
    @Listen(GroupNameChanged.class)
    public void groupnamechange(GroupNameChanged groupNameChanged, Sender sender) throws IOException {
        AccountInfo accountInfoGMC=groupNameChanged.getOperatorInfo();
        GroupInfo groupInfoGMC=groupNameChanged.getGroupInfo();
        String groupname=groupNameChanged.getAfterChange();
        String groupnamebef=groupNameChanged.getBeforeChange();
        MessageContentBuilder builder2=messageBuilderFactory.getMessageContentBuilder();
        MessageContent message1=builder2
            .at(accountInfoGMC)
            .text("将群名称由")
            .text(groupnamebef)
            .text("改为")
            .text(groupname)
            .build();
        sender.sendGroupMsg(groupInfoGMC,message1);
        Log_settler.writelog("OnGroup"+String.valueOf(groupInfoGMC));
        Log_settler.writelog(String.valueOf(groupNameChanged.getBotInfo()));
        Log_settler.writelog(String.valueOf(message1)+"\n\n\n");
    }

    


}

