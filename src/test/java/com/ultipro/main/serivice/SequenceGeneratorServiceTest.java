package com.ultipro.main.serivice;

import com.ultipro.main.Beans.DatabaseSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RunWith(SpringJUnit4ClassRunner.class)
public class SequenceGeneratorServiceTest {

    @Mock
    MongoOperations mongoOperations;

    @InjectMocks
    SequenceGeneratorService sequenceGeneratorService;

    @Test
    public void testGenerateSequenceMethodWhenUserEntersSeqNameThenRetunsLongNumber() throws Exception {
        DatabaseSequence counter = new DatabaseSequence();
        Mockito.when(mongoOperations.findAndModify(query(where("_id").is("seqName")), new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                                                   DatabaseSequence.class)).thenReturn(counter);
        Assert.assertEquals(sequenceGeneratorService.generateSequence("seqName"), 1);

    }
}
