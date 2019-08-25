package com.dzuniga.JsonDiff;

import static org.assertj.core.api.Assertions.assertThat;

import com.dzuniga.JsonDiff.functions.DefaultInsightsFinder;
import com.dzuniga.JsonDiff.functions.InsightsFinder;
import com.dzuniga.JsonDiff.model.DiffItem;
import java.util.Collection;
import org.junit.Test;

public class DefaultInsightsFinderTest {

    private InsightsFinder insightsFinder = new DefaultInsightsFinder();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenLeftIsBlank() {
        String left = "";
        String right = "random text";

        insightsFinder.getDiffInsights(left, right);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenLeftIsNull() {
        String left = null;
        String right = "random text";

        insightsFinder.getDiffInsights(left, right);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRightIsBlank() {
        String right = "";
        String left = "random text";

        insightsFinder.getDiffInsights(left, right);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenRightIsNull() {
        String right = null;
        String left = "random text";

        insightsFinder.getDiffInsights(left, right);
    }

    @Test
    public void shouldFindNODiffItem() {
        String left =   "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";
        String right =  "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";

        Collection<DiffItem> actual = insightsFinder.getDiffInsights(left, right);

        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldFindOneDiffItem() {
        String left =   "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";
        String right =  "{ \"glossary\": { \"titte\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";

        Collection<DiffItem> actual = insightsFinder.getDiffInsights(left, right);

        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual).hasSize(1);
        assertThat(actual).contains(DiffItem.builder().offset(20).length(1).build());
    }

    @Test
    public void shouldFindTwoDiffItem() {
        String left =   "{ \"glossary\": { \"title\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"] }, \"GlossSee\": \"markup\" } } } } }";
        String right =  "{ \"glossary\": { \"titte\": \"example glossary\", \"GlossDiv\": { \"title\": \"S\", \"GlossList\": { \"GlossEntry\": { \"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\": { \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"JSO\"] }, \"GlossSee\": \"markup\" } } } } }";

        Collection<DiffItem> actual = insightsFinder.getDiffInsights(left, right);

        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual).hasSize(2);
        assertThat(actual).contains(DiffItem.builder().offset(20).length(1).build());
        assertThat(actual).contains(DiffItem.builder().offset(358).length(3).build());
    }
}