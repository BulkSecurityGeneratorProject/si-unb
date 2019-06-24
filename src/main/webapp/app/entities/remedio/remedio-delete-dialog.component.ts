import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRemedio } from 'app/shared/model/remedio.model';
import { RemedioService } from './remedio.service';

@Component({
  selector: 'jhi-remedio-delete-dialog',
  templateUrl: './remedio-delete-dialog.component.html'
})
export class RemedioDeleteDialogComponent {
  remedio: IRemedio;

  constructor(protected remedioService: RemedioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.remedioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'remedioListModification',
        content: 'Deleted an remedio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-remedio-delete-popup',
  template: ''
})
export class RemedioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ remedio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RemedioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.remedio = remedio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/remedio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/remedio', { outlets: { popup: null } }]);
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
