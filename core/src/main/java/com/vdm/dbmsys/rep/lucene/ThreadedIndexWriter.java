package com.vdm.dbmsys.rep.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadedIndexWriter
    extends org.apache.lucene.index.IndexWriter {
    private ExecutorService threadPool;
    private Analyzer defaultAnalyzer;

    private class Job implements Runnable {
        private Document doc;

        public Job(Document doc) {
            this.doc = doc;
        }

        public void run() {
            try {
                ThreadedIndexWriter.super.addDocument(doc);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }

    public ThreadedIndexWriter(Directory dir, IndexWriterConfig iwc,
                               int numThreads,
                               int maxQueueSize)
        throws CorruptIndexException, IOException {
        super(dir, iwc);
        threadPool = new ThreadPoolExecutor(
            numThreads, numThreads,
            0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(maxQueueSize, false),
            new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void updateDocument(Document doc) {
        threadPool.execute(new Job(doc));
    }

    public void close()
        throws CorruptIndexException, IOException {
        finish();
        super.close();
    }

    public void close(boolean doWait)
        throws CorruptIndexException, IOException {
        finish();
        super.close();
    }

    public void rollback()
        throws CorruptIndexException, IOException {
        finish();
        super.rollback();
    }

    private void finish() {
        threadPool.shutdown();
        while (true) {
            try {
                if (threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ie);
            }
        }
    }
}
