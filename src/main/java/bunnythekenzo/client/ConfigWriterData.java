package bunnythekenzo.client;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record ConfigWriterData(Supplier supplier, Consumer consumer) {}
