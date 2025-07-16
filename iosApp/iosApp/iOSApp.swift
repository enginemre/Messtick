import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init(){
        // KMM - Logger Call
        HelperKt.doInitLogger()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}