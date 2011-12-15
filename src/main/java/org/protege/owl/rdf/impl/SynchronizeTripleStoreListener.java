package org.protege.owl.rdf.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.openrdf.repository.RepositoryException;
import org.protege.owl.rdf.api.OwlTripleStore;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.RemoveAxiom;

public class SynchronizeTripleStoreListener implements OWLOntologyChangeListener {
	private Logger LOGGER = Logger.getLogger(SynchronizeTripleStoreListener.class);
	private OwlTripleStore ots;

	public SynchronizeTripleStoreListener(OwlTripleStore ots) {
		this.ots = ots;
	}

	public void ontologiesChanged(List<? extends OWLOntologyChange> changes) {
		for (OWLOntologyChange change : changes) {
			try {
				if (change instanceof AddAxiom) {
					ots.addAxiom(((AddAxiom) change).getAxiom());
				}
				else if (change instanceof RemoveAxiom) {
					ots.removeAxiom(((RemoveAxiom) change).getAxiom());
				}
			}
			catch (RepositoryException re) {
				LOGGER.warn("Exception caught makeing change", re);
			}
		}
	}
}