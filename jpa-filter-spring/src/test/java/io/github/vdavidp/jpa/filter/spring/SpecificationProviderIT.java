/*
 * The MIT License
 *
 * Copyright 2021 david.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.vdavidp.jpa.filter.spring;

import io.github.vdavidp.jpa.filter.spring.example.Article;
import io.github.vdavidp.jpa.filter.spring.example.Comment;
import io.github.vdavidp.jpa.filter.spring.example.CommentRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author david
 */
@DataJpaTest
@Import(JpaFilterAutoconfigure.class)
public class SpecificationProviderIT {
  
  @Autowired
  CommentRepository commentRepository;
  
  @Autowired
  SpecificationProvider specificationProvider;
  
  @Test
  void withFilter() {
    String filter = "author:'john'";
    manualSpecificationCreation(filter, 1);
  }
  
  @Test
  void withNoFilter() {
    manualSpecificationCreation(null, 3);
  }
  
  private void manualSpecificationCreation(String filter, int expectedTotal) {
    Specification<Comment> spec = specificationProvider.create(filter, Comment.class);
    List<Comment> comments = commentRepository.findAll(spec);
    
    assertEquals(expectedTotal, comments.size());
  }
  
}
