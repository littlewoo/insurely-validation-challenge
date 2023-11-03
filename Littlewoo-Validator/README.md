
# Insurely Tech Challenge Notes

My solution for Insurely's tech challenge: 
https://docs.google.com/document/d/1eJNfLmBU83VGx63NKcL5lTC9Yp9F1Rj3GGK7Ty3k9Lk/edit?usp=sharing

## My assumptions and decisions

 - The framework should be compatible for common use cases for validation of data, such as:
   - validating data coming into an application from external interfaces (e.g. user input)
   - validating data before it is persisted to a database
   - validating data as classes are being instantiated in order to enforce invariants
 - Two different user experiences ("user" as in the programmer using the framework) might be desirable:
   - an imperative pattern with e.g. a method call with the data to be validated which throws an exception if the data 
     does not meet the requirements of the rule 
     - can be used, e.g. in constructors or http request handlers for the programmer to manually validate data according
       to a known set of rules. 
   - a more declarative pattern where the rules for a data structure are declared in the data structure class itself, 
     and validation is performed automatically e.g. when the class is instantiated
     - can be used by the developer to ask the question, "Is this object valid"
 - Flexibility - i.e. the ability for the user to define whatever application behaviour they want - is desirable.
   - e.g. the user should be able to define their own validation rules, and the behaviour of the framework when a rule
     is violated.
   - Some sort of `ValidationResult` structure seems like a good way to achieve this. 
   - Modelling such a result after the monad-like types in the Java Standard Library (such as `Optional` and `Stream`) 
     seems like a good tradeoff between flexibility and ease of use.

## My approach

### Iterations

1. Build a simple framework that can imperatively validate a single type of data (e.g. `int` or `str`) according to a 
   single rule (e.g. string is not blank). 
2. Make the framework generic, to enable validation of arbitrary types of data, with some interface for implementation
of custom validation rules.
3. Write tests which implement a custom validation rule for Swedish personnummer and for Swedish personal names.

#### Stretch goals I would do with more time:

4. Add annotation processing to automatically generate validation rules for data classes.
   - This would enable the declarative pattern of validation.
5. Add support for validation of multiple rules on a single field of data.
   - This is somewhat done with the `CompositeValidationRule` class, but it would end up somewhat ugly to use with the 
     annotation processing approach. 
