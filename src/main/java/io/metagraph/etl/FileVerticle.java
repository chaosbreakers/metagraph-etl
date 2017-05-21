package io.metagraph.etl;

import com.google.common.collect.Maps;
import io.metagraph.etl.reader.Reader;
import io.metagraph.etl.reader.config.ReaderConfig;
import io.metagraph.etl.reader.impl.CSVReader;
import io.metagraph.etl.writer.Writer;
import io.metagraph.etl.writer.impl.MockWriter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.parsetools.RecordParser;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by eguoyix on 17/5/21.
 */
public class FileVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(Startup.class);
    
    ReaderConfig readerConfig = new ReaderConfig() {
        @Override
        public String getLabel() {
            return null;
        }

        @Override
        public String getBidField() {
            return null;
        }

        @Override
        public List<String> getOutEdgeFields() {
            return null;
        }
    };
    Map<Integer,String> csvHeader = Maps.newHashMap();
    private Reader reader = new CSVReader(csvHeader,readerConfig);

    private Writer writer = new MockWriter();
    

    @Override
    public void start() throws Exception {
        super.start();
        FileSystem fs = vertx.fileSystem();
        RecordParser parser = RecordParser.newDelimited(System.getProperty("line.separator", "\n"),
                record -> logger.info("record read from file: {0}", record.toString()));
        fs.open("names.txt", new OpenOptions(), ar -> {
            if (ar.succeeded()) {
                AsyncFile file = ar.result();
                file.handler(parser);
                file.endHandler(h -> {
                    parser.handle(Buffer.buffer(System.getProperty("line.separator", "\n")));// add this to read the last buffer
                    vertx.close();
                });
            } else {
                logger.error("can't find file");
            }
        });

    }
    
    private Object processRecord(String record){
        Map<String, Object> vertexAndEdges = reader.read(readerConfig, record);
        return writer.write("",vertexAndEdges);
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new FileVerticle());
    }
}
