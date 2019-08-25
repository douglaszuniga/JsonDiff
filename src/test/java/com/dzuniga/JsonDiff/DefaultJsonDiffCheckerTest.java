package com.dzuniga.JsonDiff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.dzuniga.JsonDiff.functions.DefaultInsightsFinder;
import com.dzuniga.JsonDiff.functions.DefaultJsonDiffChecker;
import com.dzuniga.JsonDiff.functions.InsightsFinder;
import com.dzuniga.JsonDiff.functions.JsonDiffChecker;
import com.dzuniga.JsonDiff.model.DiffItem;
import com.dzuniga.JsonDiff.model.DiffResult;
import com.dzuniga.JsonDiff.model.Result;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultJsonDiffCheckerTest {

    private InsightsFinder insightsFinder;
    private JsonDiffChecker handler;

    @Before
    public void setUp() {
        insightsFinder = mock(DefaultInsightsFinder.class);
        handler = new DefaultJsonDiffChecker(insightsFinder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenLeftIsBlank() {
        handler.check("", "some string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenLeftIsNull() {
        handler.check(null, "some string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRightIsBlank() {
        handler.check("some string", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRightIsNull() {
        handler.check("some string", null);
    }

    @Test
    public void shouldReturnEqualResultWhenBothAreEquals() {
        String left = "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";

        DiffResult actual = handler.check(left, left);

        assertThat(actual).isNotNull();
        assertThat(actual.getResult()).isEqualTo(Result.EQUAL);
        assertThat(actual.getInsight()).isNull();
    }

    @Test
    public void shouldReturnNotEqualSizeResultWhenBothAreNotEqualsAndNotTheSameSize() {
        String left = "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";
        String right = "{ \"glossary\": { \"title\": \"example glossary\", \"title2\": \"example2 glossary2\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";;

        DiffResult actual = handler.check(left, right);

        assertThat(actual).isNotNull();
        assertThat(actual.getResult()).isEqualTo(Result.NOT_EQUAL_SIZE);
        assertThat(actual.getInsight()).isNull();
    }

    @Test
    public void shouldReturnEqualSizeResultWhenBothAreNotEqualsButEqualSize() {
        String left =   "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";
        String right =  "{ \"yrazzolg\": { \"title\": \"example glossary\", \"DivGloss\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";;

        when(insightsFinder.getDiffInsights(anyString(), anyString())).thenReturn(
                List.of(DiffItem.builder().length(1).offset(1).build(),
                        DiffItem.builder().length(1).offset(2).build()));

        DiffResult actual = handler.check(left, right);

        assertThat(actual).isNotNull();
        assertThat(actual.getResult()).isEqualTo(Result.EQUAL_SIZE);
        assertThat(actual.getInsight()).isNotNull();
        assertThat(actual.getInsight()).isNotEmpty();
        assertThat(actual.getInsight()).hasSize(2);
    }
}