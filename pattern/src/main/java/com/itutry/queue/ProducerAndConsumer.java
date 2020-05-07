package com.itutry.queue;

import com.itutry.suspension.Downloader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步模式之生产者/消费者, 用消息队列来平衡生产和消费的线程资源
 *
 * @author itutry
 * @create 2020-05-06_16:36
 */
@Slf4j(topic = "c.ProducerAndConsumer")
public class ProducerAndConsumer {

  public static void main(String[] args) {
    final MessageQueue messageQueue = new MessageQueue(2);
    for (int i = 0; i < 4; i++) {
      int id = i;
      new Thread(() -> {
        try {
          log.debug("download...");
          final List<String> list = Downloader.download();
          log.debug("try put message({})", id);
          messageQueue.put(new Message(id, list));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }, "生产者" + i).start();
    }

    new Thread(() -> {
      while (true) {
        Message message = messageQueue.take();
        List<String> response = (List<String>) message.getMessage();
        log.debug("take message({}): [{}] lines", message.getId(), response.size());
      }
    }, "消费者").start();
  }

  private static class Message {

    private int id;
    private Object message;

    public Message(int id, Object message) {
      this.id = id;
      this.message = message;
    }

    public int getId() {
      return id;
    }

    public Object getMessage() {
      return message;
    }
  }

  @Slf4j(topic = "c.MessageQueue")
  private static class MessageQueue {

    private LinkedList<Message> queue;
    private int capacity;

    public MessageQueue(int capacity) {
      this.capacity = capacity;
      queue = new LinkedList<>();
    }

    public Message take() {
      synchronized (queue) {
        while (queue.isEmpty()) {
          log.debug("没货了, wait");
          try {
            queue.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        final Message message = queue.removeFirst();
        queue.notifyAll();
        return message;
      }
    }

    public void put(Message message) {
      synchronized (queue) {
        while (queue.size() == capacity) {
          log.debug("库存已达上限, wait");
          try {
            queue.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        queue.addLast(message);
        queue.notifyAll();
      }
    }
  }
}
