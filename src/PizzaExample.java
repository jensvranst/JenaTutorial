
import java.util.Iterator;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class PizzaExample
{
    /***********************************/
    /* Constants                       */
    /***********************************/
    public static String BASE = "http://www.co-ode.org/ontologies/pizza/pizza.owl";
    public static String NS = BASE + "#";

    /***********************************/
    /* External signature methods      */
    /***********************************/

    public static void main( String[] args ) {
        new PizzaExample().run();
    }

    public void run() {
        OntModel m = getPizzaOntology();
        OntClass pizza = m.getOntClass( NS + "Cajun" );

        for (Iterator<OntClass> supers = pizza.listSuperClasses(); supers.hasNext(); ) {
            displayType( supers.next() );
        }
    }

    /***********************************/
    /* Internal implementation methods */
    /***********************************/

    protected OntModel getPizzaOntology() {
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
        m.read( BASE );
        return m;
    }

    protected void displayType( OntClass sup ) {
        if (sup.isRestriction()) {
            displayRestriction( sup.asRestriction() );
        }
    }

    protected void displayRestriction( Restriction sup ) {
        if (sup.isAllValuesFromRestriction()) {
            displayRestriction( "all", sup.getOnProperty(), sup.asAllValuesFromRestriction().getAllValuesFrom() );
        }
        else if (sup.isSomeValuesFromRestriction()) {
            displayRestriction( "some", sup.getOnProperty(), sup.asSomeValuesFromRestriction().getSomeValuesFrom() );
        }
    }

    protected void displayRestriction( String qualifier, OntProperty onP, Resource constraint ) {
        String out = String.format( "%s %s %s",
                                    qualifier, renderURI( onP ), renderConstraint( constraint ) );
        System.out.println( "this pizza: " + out );
    }

    protected Object renderConstraint( Resource constraint ) {
        if (constraint.canAs( UnionClass.class )) {
            UnionClass uc = constraint.as( UnionClass.class );
           
            String r = "union{ ";
            for (Iterator<? extends OntClass> i = uc.listOperands(); i.hasNext(); ) {
                r = r + " " + renderURI( i.next() );
            }
            return r + "}";
        }
        else {
            return renderURI( constraint );
        }
    }

    protected Object renderURI( Resource onP ) {
        String qName = onP.getModel().qnameFor( onP.getURI() );
        return qName == null ? onP.getLocalName() : qName;
    }
}