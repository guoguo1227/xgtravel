/*[[
    /module/scrollable/scrollable.css,
]]*/

angular.module('lg.scrollable', []).
    directive('lgScrollable', [function() {
    return {
        restrict: 'A',
        scope: {
            maxHeight: '@'
        },
        link: function(scope, element) {
            scope.maxHeight = scope.maxHeight || '200px';

            var scrollable = false;
            var innerHeight, outerHeight, barHeight, scrollHeight;
            var curTop = 0, lastDelta = 0, lastDeltaAbs = 0, maxScroll;

            var scrollBody = angular.element(element.children()[0]);
            var scrollbar = angular.element('<div class="lg-scrollbar"></div>');
            element.append(scrollbar);

            element.css({'max-height': scope.maxHeight});
            element.addClass('lg-scrollable');
            scrollBody.addClass('lg-scroll-body');

            element.bind('mouseenter', function () {
                outerHeight = element[0].offsetHeight;
                innerHeight = scrollBody[0].offsetHeight;

                maxScroll = innerHeight - outerHeight;
                scrollable = (innerHeight > outerHeight);
                scrollable && scrollbar.css({display: 'block'}) || scrollbar.css({display: 'none'});
                barHeight = scrollbar[0].offsetHeight;
                scrollHeight = outerHeight - barHeight;
            });
            element.bind('mouseleave', function() {
                curTop = 0;
                lastDelta = 0;
                lastDeltaAbs = 0;

                scrollable && scrollbar.css({display: 'none'});
                scrollable && scrollbar.css({top: 0});
                scrollable && scrollBody.css({top: 0});
            });

            element.bind('mousewheel', function (event) {
                event.stopPropagation();
                event.preventDefault();
                if(!scrollable) return;

                var delta = event.deltaY;
                var deltaAbs = Math.abs(delta);
                if (deltaAbs > lastDeltaAbs) {
                    curTop += (delta - lastDelta);
                } else {
                    curTop += delta;
                }
                lastDelta = delta;
                lastDeltaAbs = deltaAbs;

                if (curTop < 0) {
                    curTop = 0;
                    scrollBody.css({top: 0});
                } else if (curTop < maxScroll) {
                    scrollBody.css({top: -curTop + 'px'});
                } else {
                    scrollBody.css({top: -maxScroll + 'px'});
                    curTop = maxScroll;
                }

                scrollbar.css({top: (curTop / maxScroll * scrollHeight) + 'px'})
            });
        }
    };
}]);
