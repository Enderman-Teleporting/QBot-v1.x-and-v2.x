package io.github.et.messager;

import io.github.et.exceptions.messageExceptions.IllegalMessageDealingException;
import io.github.ettoolset.tools.logger.Logger;
import io.github.ettoolset.tools.logger.LoggerNotDeclaredException;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
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
    public void runRepeat(MessageEvent msgEvent) throws LoggerNotDeclaredException {
        long groupId = msgEvent.getSubject().getId();
        String msgContent = msgEvent.getMessage().contentToString();

        if (lastMessageMap.containsKey(groupId) && lastMessageMap.get(groupId) != null) {
            if (lastMessageMap.get(groupId).equals(msgContent)) {
                int count = messageCountMap.getOrDefault(groupId, 1);
                if (count == 1) {
                    msgEvent.getSubject().sendMessage(msgEvent.getMessage());
                    messageCountMap.put(groupId, count + 1);
                    Logger logger = Logger.getDeclaredLogger();
                    logger.info("Repeated message at %s", groupId);
                } else {
                    messageCountMap.put(groupId, count + 1);
                }
            } else {
                lastMessageMap.put(groupId, msgContent);
                messageCountMap.put(groupId, 1);
            }
        } else {
            lastMessageMap.put(groupId, msgContent);
            messageCountMap.put(groupId, 1);
        }
    }
}
