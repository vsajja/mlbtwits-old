import ratpack.groovy.template.MarkupTemplateModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
  }

  handlers {
    get {
      render 'hello world'
    }

    get('stats') {
      def mlbPlayerId = request.queryParams['mlbPlayerId']

      def stats = []

      // avg runs hr rbi sb
      // w ks era whip sv

      render mlbPlayerId.toString()
    }

    files { dir "public" }
  }
}
