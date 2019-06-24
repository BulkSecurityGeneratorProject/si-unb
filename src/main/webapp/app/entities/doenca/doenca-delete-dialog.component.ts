import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDoenca } from 'app/shared/model/doenca.model';
import { DoencaService } from './doenca.service';

@Component({
  selector: 'jhi-doenca-delete-dialog',
  templateUrl: './doenca-delete-dialog.component.html'
})
export class DoencaDeleteDialogComponent {
  doenca: IDoenca;

  constructor(protected doencaService: DoencaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.doencaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'doencaListModification',
        content: 'Deleted an doenca'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-doenca-delete-popup',
  template: ''
})
export class DoencaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ doenca }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DoencaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.doenca = doenca;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/doenca', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/doenca', { outlets: { popup: null } }]);
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
