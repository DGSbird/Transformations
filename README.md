# README #

### Overview ###

Maven project comprising relevant objects / functionality for the representation of transformations in the BIRD model. The structure of transformations in the BIRD model is based on the SDMX information model for transformations (see SDMX Technical Specifications, Section 2 Information model, 13.2.1 Class Diagram (https://sdmx.org/)). 
The core component of this project is the class TTree that allows to transform (see TTree.transform()) an Abstract syntax tree (extracted from a valid vtl expression) into it's representation in the BIRD model. Additionally the (tree related objects) are already defined as jpa entities (see for example annotation in class TScheme) such that the resulting tree structure can be persisted easily into a jpa context.

### How do I get set up? ###

1. Clone the repository

### Contact ###

For questions related to this repository please contact BIRD@ecb.europa.eu.