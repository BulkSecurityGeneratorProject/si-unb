import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFarmacia } from 'app/shared/model/farmacia.model';
import { FarmaciaService } from './farmacia.service';

@Component({
  selector: 'jhi-farmacia-delete-dialog',
  templateUrl: './farmacia-delete-dialog.component.html'
})
export class FarmaciaDeleteDialogComponent {
  farmacia: IFarmacia;

  constructor(protected farmaciaService: FarmaciaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.farmaciaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'farmaciaListModification',
        content: 'Deleted an farmacia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-farmacia-delete-popup',
  template: ''
})
export class FarmaciaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ farmacia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FarmaciaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.farmacia = farmacia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/farmacia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/farmacia', { outlets: { popup: null } }]);
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
