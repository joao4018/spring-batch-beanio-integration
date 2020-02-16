package br.com.renanravelli.batch.configuration.step;

import br.com.renanravelli.batch.streams.enums.StreamName;
import br.com.renanravelli.batch.streams.mapping.Registry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.beanio.StreamFactory;
import org.beanio.spring.BeanIOFlatFileItemReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
@RequiredArgsConstructor
public class UserStepReaderFile {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    @SneakyThrows
    public ItemReader<Registry> userItemReaderFile(@Qualifier("streamFactory") StreamFactory streamFactory,
                                                   @Value("${file.directory.out}") String path) {
        BeanIOFlatFileItemReader<Registry> reader = new BeanIOFlatFileItemReader<>();
        reader.setResource(new FileSystemResource(
                path.concat(File.separator)
                        .concat(StreamName.USER_CSV.getStream()
                                .concat(StreamName.USER_CSV.getExtesion()))));
        reader.setStreamName(StreamName.USER_CSV.getStream());
        reader.setStreamFactory(streamFactory);
        reader.open(new ExecutionContext());
        reader.afterPropertiesSet();

        return reader;
    }

    /**
     * Step responsavel por realizar a execucao dos itens reader e writer.
     */
    @Bean("stepReaderFileUsers")
    public Step stepReaderFileUsers(@Qualifier("userItemReaderFile") ItemReader reader,
                                    @Qualifier("userItemWriterFile") ItemWriter writer) {
        return this.stepBuilderFactory.get("STEP_READER_USERS_IN_FILE")
                .chunk(1000)
                .reader(reader)
                .writer(writer)
                .build();
    }


}
