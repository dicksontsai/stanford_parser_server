package com.dicksontsai;

import java.util.List;
import java.io.StringReader;

import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

/**
 * ParserWrapper loads an English parser and offers basic methods.
 */
class ParserWrapper {
    private LexicalizedParser lp;

    ParserWrapper() {
        this.lp = LexicalizedParser.loadModel();
    }

    /**
     * Return the Penn syntax tree for a sentence.
     */
    public String parseSentence(String sentence) {
        TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(sentence));
        List<CoreLabel> rawWords2 = tok.tokenize();
        Tree parse = lp.apply(rawWords2);
        return parse.pennString();
    }
}
