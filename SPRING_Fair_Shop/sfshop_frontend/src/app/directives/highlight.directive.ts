import { Directive, Renderer2, ElementRef, OnInit, HostListener } from '@angular/core';


@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective implements OnInit {



  constructor(private elRef: ElementRef, private renderer: Renderer2) {
  }

  ngOnInit() {
  }

  @HostListener('mouseenter') mouseover(eventData: Event) {
    this.renderer.setStyle(this.elRef.nativeElement, 'background-color', '#dedede');
  }

  @HostListener('mouseleave') mouseleave(eventData: Event) {
    this.renderer.setStyle(this.elRef.nativeElement, 'background-color', 'transparent');
  }
}
