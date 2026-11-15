package bunnythekenzo.client;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ClickRateTracker {
  public static final Deque<Long> leftClicks = new ArrayDeque();
  public static final Deque<Long> rightClicks = new ArrayDeque();

  public static void recordClick(int intValue) {
    Deque<Long> deque3 = intValue == 0 ? leftClicks : (intValue == 1 ? rightClicks : null);
    if (deque3 != null) {
      synchronized (deque3) {
        deque3.addLast(System.nanoTime());
      }
    }
  }

  public static int getClicksPerSecond(int intValue) {
    Deque<Long> deque3 = intValue == 0 ? leftClicks : rightClicks;
    long longValue = System.nanoTime() - 1000000000L;
    synchronized (deque3) {
      while (!deque3.isEmpty() && deque3.peekFirst() < longValue) {
        deque3.pollFirst();
      }

      return deque3.size();
    }
  }
}
