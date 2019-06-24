import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedico } from 'app/shared/model/medico.model';
import { MedicoService } from './medico.service';

@Component({
  selector: 'jhi-medico-delete-dialog',
  templateUrl: './medico-delete-dialog.component.html'
})
export class MedicoDeleteDialogComponent {
  medico: IMedico;

  constructor(protected medicoService: MedicoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'medicoListModification',
        content: 'Deleted an medico'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-medico-delete-popup',
  template: ''
})
export class MedicoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medico }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MedicoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.medico = medico;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/medico', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/medico', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
