package io.github.et.messager;

import io.github.et.exceptions.messageExceptions.IllegalMessageDealingException;
import io.github.ettoolset.tools.logger.Logger;
import io.github.ettoolset.tools.logger.LoggerNotDeclaredException;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@SuppressWarnings("unused")
public class Repeater extends SimpleListenerHost {
    private final HashMap<Long, String> lastMessageMap = new HashMap<>();
    private final HashMap<Long, Integer> messageCountMap = new HashMap<>();

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        try {
            Logger logger = Logger.getDeclaredLogger();
            logger.error("Exception occurred when handling a repeating operation, error info as follows:");
        } catch (LoggerNotDeclaredException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalMessageDealingException("Exception occurred when dealing with MessageEvent", exception);
    }

    @EventHandler
    public void runRepeat(GroupMessageEvent msgEvent) throws LoggerNotDeclaredException {
        long groupId = msgEvent.getSubject().getId();
        String msgContent = msgEvent.getMessage().serializeToMiraiCode();
        if(!messageCountMap.containsKey(groupId)) {
            messageCountMap.put(groupId,0);
        }
        if(!lastMessageMap.containsKey(groupId)) {
            lastMessageMap.put(groupId,"");
        }
        if(lastMessageMap.get(groupId).isEmpty()||(!lastMessageMap.get(groupId).equals(msgContent))) {
            lastMessageMap.put(groupId,msgContent);
            messageCountMap.put(groupId,1);
        }else{
            messageCountMap.put(groupId,messageCountMap.get(groupId)+1);
            if(messageCountMap.get(groupId)==2){
                msgEvent.getSubject().sendMessage(msgEvent.getMessage());
                Logger logger=Logger.getDeclaredLogger();
                logger.info("Handled Repeating event at group " +groupId);
            }
        }
    }
}
