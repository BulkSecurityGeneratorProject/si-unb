import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstoque } from 'app/shared/model/estoque.model';
import { EstoqueService } from './estoque.service';

@Component({
  selector: 'jhi-estoque-delete-dialog',
  templateUrl: './estoque-delete-dialog.component.html'
})
export class EstoqueDeleteDialogComponent {
  estoque: IEstoque;

  constructor(protected estoqueService: EstoqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estoqueService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estoqueListModification',
        content: 'Deleted an estoque'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estoque-delete-popup',
  template: ''
})
export class EstoqueDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estoque }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstoqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estoque = estoque;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estoque', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estoque', { outlets: { popup: null } }]);
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
