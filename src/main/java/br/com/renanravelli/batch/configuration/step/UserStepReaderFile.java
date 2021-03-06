package br.com.renanravelli.batch.configuration.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renanravelli
 * @Maintainer joao4018
 * @since 20/03/2020
 */
@Configuration
@RequiredArgsConstructor
public class UserStepReaderFile {

    private final StepBuilderFactory stepBuilderFactory;

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
